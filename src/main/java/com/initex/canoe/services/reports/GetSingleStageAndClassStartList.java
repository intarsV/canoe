package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.dto.ResultQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetSingleStageAndClassStartList {

    public List<RaceResultList> execute(List<RaceResultList> list, ResultQuery q) {

        List<RaceResultList> filteredTmp;
        List<RaceResultList> filtered = new ArrayList<>();

        switch (q.getSubEvent()) {
            case "Heat1":
                filtered = new ArrayList<>(list);
                break;
            case "Heat2":
                filtered = list.stream()
                        .filter(s -> !s.getHeatOneStage().isQualified())
                        .sorted(Comparator.comparing(RaceResultList::getBib).reversed())
                        .collect(Collectors.toList());
                break;
            case "Semi-final":
                filteredTmp = list.stream()
                        .filter(e -> e.getHeatOneStage().isQualified())
                        .sorted((o1, o2) -> o2.getHeatOneStage().getTotal().compareTo(o1.getHeatOneStage().getTotal()))
                        .collect(Collectors.toList());
                filtered = list.stream()
                        .filter(e -> e.getHeatTwoStage().isQualified())
                        .sorted((o1, o2) -> o2.getHeatTwoStage().getTotal().compareTo(o1.getHeatTwoStage().getTotal()))
                        .collect(Collectors.toList());
                filtered.addAll(filteredTmp);
                break;
            case "Final":
                switch (q.getEventFormat()) {
                    case 6:
                        filtered = list.stream()
                                .filter(e -> e.getHeatOneStage().isQualified())
                                .sorted((o1, o2) -> o2.getHeatOneStage().getTotal().compareTo(o1.getHeatOneStage().getTotal()))
                                .collect(Collectors.toList());
                        break;
                    case 3:
                        filtered = list.stream()
                                .filter(e -> e.getSemiFinalStage().isQualified())
                                .sorted((o1, o2) -> o2.getSemiFinalStage().getTotal().compareTo(o1.getSemiFinalStage().getTotal()))
                                .collect(Collectors.toList());
                        break;
                    case 2:
                        filteredTmp = list.stream()
                                .filter(e -> e.getHeatOneStage().isQualified())
                                .sorted((o1, o2) -> o2.getHeatOneStage().getTotal().compareTo(o1.getHeatOneStage().getTotal()))
                                .collect(Collectors.toList());
                        filtered = list.stream()
                                .filter(e -> e.getHeatTwoStage().isQualified())
                                .sorted((o1, o2) -> o2.getHeatTwoStage().getTotal().compareTo(o1.getHeatTwoStage().getTotal()))
                                .collect(Collectors.toList());
                        filtered.addAll(filteredTmp);
                        break;
                    case 5:
                    case 7:
                        filtered = list.stream()
                                .filter(e -> e.getHeatTwoStage().isQualified())
                                .sorted(Comparator.comparing(RaceResultList::getTotalOfH1H2).reversed())
                                .collect(Collectors.toList());
                        break;
                    default:
                        return filtered;
                }
                break;
            default:
                return filtered;
        }
        return filtered;
    }
}
