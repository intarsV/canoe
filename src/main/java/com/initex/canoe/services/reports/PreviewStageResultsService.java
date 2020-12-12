package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.RaceStageResult;
import com.initex.canoe.dto.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
