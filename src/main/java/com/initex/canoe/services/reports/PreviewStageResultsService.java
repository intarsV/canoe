package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.RaceStageResult;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.services.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreviewStageResultsService {

    private ResultsServiceProcessor processor;
    private ResultListConverter converter;

    @Autowired
    public PreviewStageResultsService(ResultsServiceProcessor processor, ResultListConverter converter) {
        this.processor = processor;
        this.converter = converter;
    }

    public List<RaceStageResult> execute(ResultQuery q) {
        final List<RaceResultList> list = processor.getList(q);
        return converter.convert(list, q);
    }

    public List<RaceStageResult> executeTeams(ResultQuery q) {
        final List<RaceResultList> list = new ArrayList<>();
        for (String boatClass : Constants.getBoatClass()) {
            q.setBoatClass(boatClass);
            list.addAll(processor.getList(q));
        }
        return converter.convert(list, q);
    }
}
