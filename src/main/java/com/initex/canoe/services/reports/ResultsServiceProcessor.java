package com.initex.canoe.services.reports;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.domain.Race;
import com.initex.canoe.domain.RaceConfig;
import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.Stage;
import com.initex.canoe.dto.EventRegistryQuery;
import com.initex.canoe.dto.RaceConfigQuery;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.services.EventRegistryService;
import com.initex.canoe.services.RaceConfigService;
import com.initex.canoe.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.initex.canoe.services.utils.Constants.*;
import static com.initex.canoe.services.utils.results.CalcPenalties.countPenalties;
import static com.initex.canoe.services.utils.results.CalcRaceTime.calcRaceTime;
import static com.initex.canoe.services.utils.results.CalcTotal.calcTotal;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;

@Service
public class ResultsServiceProcessor {


    private final EventRegistryService eventRegistryService;
    private final RaceService raceService;
    private final RaceConfigService raceConfigService;

    @Autowired
    public ResultsServiceProcessor(EventRegistryService eventRegistryService, RaceService raceService, RaceConfigService raceConfigService) {
        this.eventRegistryService = eventRegistryService;
        this.raceService = raceService;
        this.raceConfigService = raceConfigService;
    }


    public List<RaceResultList> getList(final ResultQuery query) {
        final EventRegistryQuery erq = new EventRegistryQuery();
        erq.setEventId(query.getEventName());
        erq.setTeamMode(query.isTeamMode());
        final List<EventRegistry> list = eventRegistryService.getEventRegistry(erq);
        final List<RaceResultList> initialList = createResultList(list, query); //Empty result list
        processStage(initialList, query);
        return initialList;
    }

    private List<RaceResultList> createResultList(List<EventRegistry> initialList, ResultQuery query) {
        return initialList.stream()
                .filter(!isBlank(query.getBoatClass()) ?
                        e -> e.getBoatClass().getBoatClass().equals(query.getBoatClass()) : e -> true)
                .filter(query.isTeamMode() ? e -> e.isTeamMode() == query.isTeamMode() : e -> !e.isTeamMode())
                .map(e -> new RaceResultList(e, e.getBib(), new Stage(), new Stage(), new Stage(), new Stage()))
                .collect(Collectors.toList());
    }

    private List<Race> getAllResults(final ResultQuery query) {
        final List<Race> raceList = raceService.getAllRaceData(query.getEventName());
        return raceList.stream()
                .filter(!isBlank(query.getBoatClass()) ?
                        e -> e.getEventRegistry().getBoatClass().getBoatClass().equals(query.getBoatClass()) : e -> true)
                .filter(query.isTeamMode() ? e -> e.isTeamMode() == query.isTeamMode() : e -> !e.isTeamMode())
                .collect(Collectors.toList());
    }

    private void processStage(List<RaceResultList> list, ResultQuery query) {

        final List<RaceConfig> configList = raceConfigService.getList(new RaceConfigQuery(query.getEventName()));
        final List<Race> raceList = getAllResults(query); //All results

        final List<String> stageNames = Arrays.asList(HEAT_1, HEAT_2, SEMI_FINAL, FINAL);
        for (String stageName : stageNames) {
            final List<Stage> heat = new ArrayList<>();
            for (Race r : raceList) {
                if (r.getSubEvent().getSubEvent().equals(stageName)) {
                    Stage stage = new Stage();
                    stage.setRaceTime(calcRaceTime(r.getStartTime(), r.getFinishTime()));
                    stage.setPenaltyList(r.getPenaltyList());
                    stage.setPenaltiesSum(countPenalties(r.getPenaltyList()));
                    stage.setDsqr(r.isDsqr());
                    stage.setTotal(calcTotal(r.getFinishTime(), stage.getRaceTime()
                            , r.getPenaltyList(), r.isDsqr()));
                    stage.setEventRegistry(r.getEventRegistry());
                    heat.add(stage);
                }
            }

            filterBoatClass(heat, configList, stageName);
            for (Stage s : heat) {
                list.stream()
                        .filter(e -> e.getEventRegistry().equals(s.getEventRegistry()))
                        .forEach(e -> setHeatResult(e, s, stageName));
            }
        }
        if (isSpecialFormat(query) && isNotEmpty(list)) {
            processSpecialEvent(list, query, configList);
        }
    }

    private boolean isSpecialFormat(ResultQuery q) {
        final int format = q.getEventFormat();
        return format == 1 || format == 4 || format == 5 || format == 6;
    }

    private void setHeatResult(RaceResultList r, Stage s, String stageName) {
        switch (stageName) {
            case HEAT_1:
                r.setHeatOneStage(s);
                break;
            case HEAT_2:
                r.setHeatTwoStage(s);
                break;
            case SEMI_FINAL:
                r.setSemiFinalStage(s);
                break;
            case FINAL:
                r.setFinalStage(s);
                break;
            default:
                break;
        }
    }

    private void processSpecialEvent(List<RaceResultList> list, ResultQuery query, List<RaceConfig> configList) {

        final Long boatClassId = list.get(0).getEventRegistry().getBoatClass().getId();

        final RaceConfig qualified = configList.stream()
                .filter(e -> e.getBoatClass().getId().equals(boatClassId))
                .findFirst().orElse(null);

        for (RaceResultList r : list) {
            setSumMergeTotal(r, query.getEventFormat());
        }
        list.sort(Comparator.comparing(RaceResultList::getTotalOfH1H2));
        rankBoatClassSpecial(list);
        if (query.getEventFormat() == 5 || query.getEventFormat() == 6) {
            isSpecialQualified(list, requireNonNull(qualified).getHeat2());
        }
    }

    private void isSpecialQualified(List<RaceResultList> list, int qualifiedNumber) {
        for (RaceResultList i : list) {
            i.getHeatTwoStage().setQualified(i.getRankOfH1H2() <= qualifiedNumber);
        }
    }

    private void setSumMergeTotal(RaceResultList r, int format) {
        if (format == 1 || format == 5) {
            if (r.getHeatOneStage().getTotal().compareTo(r.getHeatTwoStage().getTotal()) < 0) {
                r.setTotalOfH1H2(r.getHeatOneStage().getTotal());
            } else {
                r.setTotalOfH1H2(r.getHeatTwoStage().getTotal());
            }
        }
        if (format == 4 || format == 6) {
            r.setTotalOfH1H2(r.getHeatOneStage().getTotal().add(r.getHeatTwoStage().getTotal()));
        }
    }

    private void filterBoatClass(List<Stage> list, List<RaceConfig> configList, String stageName) {
        for (String bc : BOAT_CLASS) {
            final List<Stage> boatClassStage = list.stream()
                    .filter(e -> e.getEventRegistry().getBoatClass().getBoatClass().equals(bc))
                    .sorted(Comparator.comparing(Stage::getTotal))
                    .collect(Collectors.toList());
            rankBoatClass(boatClassStage);
            int[] config = getConfigListForCurrentBoatClass(configList, bc);
            processQualification(config, stageName, boatClassStage);
        }
    }

    private void processQualification(int[] config, String stageName, List<Stage> boatClassStage) {
        for (Stage s : boatClassStage) {
            s.setQualified(isQualified(config, stageName, s));
        }
    }

    private boolean isQualified(int[] config, String stageName, Stage s) {
        switch (stageName) {
            case HEAT_1:
                return s.getRaceTime().compareTo(BigDecimal.valueOf(0)) > 0 && s.getRank() <= config[0] && !s.isDsqr();
            case HEAT_2:
                return s.getRaceTime().compareTo(BigDecimal.valueOf(0)) > 0 && s.getRank() <= config[1] && !s.isDsqr();
            case SEMI_FINAL:
                return s.getRaceTime().compareTo(BigDecimal.valueOf(0)) > 0 && s.getRank() <= config[2] && !s.isDsqr();
            default:
                return false;
        }
    }

    private int[] getConfigListForCurrentBoatClass(List<RaceConfig> configList, String boatClass) {
        for (RaceConfig rc : configList) {
            if (rc.getBoatClass().getBoatClass().equals(boatClass)) {
                return rc.getConfigList();
            }
        }
        return new int[]{0, 0, 0};
    }

    private void rankBoatClass(List<Stage> boatClass) {
        int rankInitialValue = 1;
        int rankValueTwin = 1;
        BigDecimal control = new BigDecimal(0);
        if (isNotEmpty(boatClass)) {
            for (Stage r : boatClass) {
                if (r.getRaceTime().compareTo(BigDecimal.valueOf(0)) > 0 && !r.isDsqr()) {
                    if (control.compareTo(r.getTotal()) != 0) {
                        r.setRank(rankInitialValue);
                        control = r.getTotal();
                        rankValueTwin = rankInitialValue;
                    } else {
                        r.setRank(rankValueTwin);
                        control = r.getTotal();
                    }
                    rankInitialValue++;
                }
            }
        }
    }

    private void rankBoatClassSpecial(List<RaceResultList> list) {
        int rankInitialValue = 1;
        int rankValueTwin = 1;
        BigDecimal control = new BigDecimal(0);
        if (isNotEmpty(list)) {
            for (RaceResultList r : list) {
                if (r.getTotalOfH1H2().compareTo(BigDecimal.valueOf(0)) > 0) {
                    if (control.compareTo(r.getTotalOfH1H2()) != 0) {
                        r.setRankOfH1H2(rankInitialValue);
                        control = r.getTotalOfH1H2();
                        rankValueTwin = rankInitialValue;
                    } else {
                        r.setRankOfH1H2(rankValueTwin);
                        control = r.getTotalOfH1H2();
                    }
                    rankInitialValue++;
                }
            }
        }
    }
}
