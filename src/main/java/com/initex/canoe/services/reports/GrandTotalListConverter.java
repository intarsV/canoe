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

@Component
public class GrandTotalListConverter {

    public List<RaceStageResult> convert(List<RaceResultList> rList, ResultQuery query) {

        final List<RaceStageResult> finalList = new ArrayList<>();

        rList.forEach(e -> {
            RaceStageResult rsr = new RaceStageResult(e.getEventRegistry(), setStageIndex(e), getBoatClassIndex(e.getEventRegistry()));
            rsr.setStageName(setStageName(e));
            rsr.setStage(getStage(e, query));
            finalList.add(rsr);
        });
        return finalList;
    }

    public Stage getStage(RaceResultList r, ResultQuery query) {
        if (r.getSemiFinalStage().isQualified() && r.getFinalStage() != null) {
            return r.getFinalStage();
        }
        if (r.getHeatOneStage().isQualified() || r.getHeatTwoStage().isQualified()) {
            if (query.getEventFormat() == 2) {
                return r.getFinalStage();
            }
            return r.getSemiFinalStage();
        }
        if (!r.getHeatOneStage().isQualified()) {
            return r.getHeatTwoStage();
        }
        final Stage dnsStage = new Stage();
        dnsStage.setTotal(new BigDecimal(9999));
        return dnsStage;
    }

    public int setStageIndex(RaceResultList r) {
        if (r.getFinalStage().getRank() > 0) {
            return 1;
        }
        if (r.getSemiFinalStage().getRank() > 0) {
            return 2;
        }
        return 3;
    }

    public String setStageName(RaceResultList r) {
        if (r.getFinalStage().getRank() > 0) {
            return "Final";
        }
        if (r.getSemiFinalStage().getRank() > 0) {
            return "Semi-Final";
        }
        return "Heats";
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
