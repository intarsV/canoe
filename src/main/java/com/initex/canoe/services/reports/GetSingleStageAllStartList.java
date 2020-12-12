package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.dto.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.initex.canoe.services.utils.Constants.BOAT_CLASS;

@Service
public class GetSingleStageAllStartList {

    private ResultsServiceProcessor processor;
    private GetSingleStageAndClassStartList filter;

    @Autowired
    public GetSingleStageAllStartList(ResultsServiceProcessor processor, GetSingleStageAndClassStartList filter) {
        this.processor = processor;
        this.filter = filter;
    }

    public List<RaceResultList> execute(ResultQuery q) {
        List<RaceResultList> mergedList = new ArrayList<>();
        for (String s : BOAT_CLASS) {
            q.setBoatClass(s);
            List<RaceResultList> boatClassResults = getListForBoatClass(q);
            mergedList.addAll(boatClassResults);
        }
        return mergedList;
    }

    private List<RaceResultList> getListForBoatClass(ResultQuery q) {
        List<RaceResultList> list = processor.getList(q);
        return filter.execute(list, q);
    }
}
