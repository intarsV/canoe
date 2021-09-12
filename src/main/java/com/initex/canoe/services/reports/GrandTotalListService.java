package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.RaceStageResult;
import com.initex.canoe.dto.ResultQuery;

import com.initex.canoe.services.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrandTotalListService {

    private ResultsServiceProcessor processor;
    private GrandTotalListConverter grandTotalListConverter;

    @Autowired
    public GrandTotalListService(ResultsServiceProcessor processor, GrandTotalListConverter grandTotalListConverter) {
        this.processor = processor;
        this.grandTotalListConverter = grandTotalListConverter;
    }

    public List<RaceStageResult> execute(ResultQuery q) {
        if (q.getEventName() != null) {
            List<RaceResultList> list = processor.getList(q);
            return grandTotalListConverter.convert(list, q);
        }
        return Collections.emptyList();
    }

    public List<RaceResultList> executeSumMerge(ResultQuery q) {
        if (q.getEventName() != null) {
            return processor.getList(q);
        }
        return Collections.emptyList();
    }

    public List<RaceResultList> executeSumMergeTotal(ResultQuery q) {
        List<RaceResultList> results = new ArrayList<>();
        if (q.getEventName() != null) {
            for (String boatClass : Constants.getBoatClass()) {
                q.setBoatClass(boatClass);
                results.addAll(processor.getList(q));
            }
        }
        return results;
    }
}
