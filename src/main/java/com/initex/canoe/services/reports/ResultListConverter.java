package com.initex.canoe.services.reports;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.RaceStageResult;
import com.initex.canoe.domain.result.Stage;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.exception.CanoeException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.initex.canoe.services.utils.Constants.*;

@Component
public class ResultListConverter {

    public List<RaceStageResult> convert(List<RaceResultList> rList, ResultQuery query) {

        final List<RaceStageResult> finalList = new ArrayList<>();
        final int eventFormat = query.getEventFormat();
        final Stage dnsStage = new Stage();
        dnsStage.setTotal(new BigDecimal(9999));

        switch (query.getSubEvent()) {
            case HEAT_1:
                rList.forEach(e -> {
                    RaceStageResult rsr = new RaceStageResult(e.getEventRegistry(), 1, getBoatClassIndex(e.getEventRegistry()));
                    rsr.setStage(e.getHeatOneStage() != null ? e.getHeatOneStage() : dnsStage);
                    finalList.add(rsr);
                });
                break;
            case HEAT_2:
                rList.stream()
                        .filter(e -> !e.getHeatOneStage().isQualified())
                        .forEach(e -> {
                            RaceStageResult rsr = new RaceStageResult(e.getEventRegistry(), 2, getBoatClassIndex(e.getEventRegistry()));
                            rsr.setStage(e.getHeatTwoStage() != null ? e.getHeatTwoStage() : dnsStage);
                            finalList.add(rsr);
                        });
                break;
            case SEMI_FINAL:
                rList.stream()
                        .filter(e -> e.getHeatOneStage().isQualified() || e.getHeatTwoStage().isQualified())
                        .forEach(e -> {
                            RaceStageResult rsr = new RaceStageResult(e.getEventRegistry(), 3, getBoatClassIndex(e.getEventRegistry()));
                            rsr.setStage(e.getSemiFinalStage() != null ? e.getSemiFinalStage() : dnsStage);
                            finalList.add(rsr);
                        });
                break;
            case FINAL:
                rList.stream()
                        .filter(e -> isFinalQualified(eventFormat, e))
                        .forEach(e -> {
                            RaceStageResult rsr = new RaceStageResult(e.getEventRegistry(), 4, getBoatClassIndex(e.getEventRegistry()));
                            rsr.setStage(e.getFinalStage() != null ? e.getFinalStage() : dnsStage);
                            finalList.add(rsr);
                        });
                break;
            default:
                rList.forEach(e -> finalList.add(new RaceStageResult(e.getEventRegistry(), 2, getBoatClassIndex(e.getEventRegistry()), dnsStage)));
        }
        return finalList;
    }

    public boolean isFinalQualified(int format, RaceResultList e) {
        switch (format) {
            case 3:
                return e.getSemiFinalStage().isQualified();
            case 2:
                return e.getHeatOneStage().isQualified() || e.getHeatTwoStage().isQualified();
            case 5:
            case 6:
                return e.getHeatTwoStage().isQualified();
            default:
                return false;
        }
    }

    public int getBoatClassIndex(EventRegistry e) {
        switch (e.getBoatClass().getBoatClass()) {
            case "C1 Women":
                return 1;
            case "C1 Men":
                return 2;
            case "K1 Women":
                return 3;
            case "K1 Men":
                return 4;
            default:
                throw new CanoeException("Nu be, čo takoje!");
        }
    }
}
