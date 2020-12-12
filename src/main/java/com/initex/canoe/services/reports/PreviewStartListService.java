package com.initex.canoe.services.reports;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.dto.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isBlank;

@Service
public class PreviewStartListService {

    private ResultsServiceProcessor processor;
    private GetSingleStageAndClassStartList filter;
    private GetSingleStageAllStartList filterTwo;

    @Autowired
    public PreviewStartListService(ResultsServiceProcessor processor, GetSingleStageAndClassStartList filter,
                                   GetSingleStageAllStartList filterTwo) {
        this.processor = processor;
        this.filter = filter;
        this.filterTwo = filterTwo;
    }

    public List<RaceResultList> execute(ResultQuery q) {

        final List<RaceResultList> list = processor.getList(q);
        if (q.getEventName() != null && q.getEventName() != 0) {
            if (!isBlank(q.getBoatClass())) {
                if (!isBlank(q.getSubEvent())) {
                    //process start list for current boat class && current stage OK
                    final List<RaceResultList> filtered = list.stream()
                            .sorted(Comparator.comparing(RaceResultList::getBib).reversed())
                            .collect(Collectors.toList());
                    return filter.execute(filtered, q);
                } else {
                    //process initial start list for current boat class OK
                    return list.stream()
                            .filter(e -> e.getEventRegistry().getBoatClass().getBoatClass().equals(q.getBoatClass()))
                            .sorted(Comparator.comparing(RaceResultList::getBib).reversed())
                            .collect(Collectors.toList());
                }
            } else if (!isBlank(q.getSubEvent())) {
                //process initial start list for all boat classes current stage
                return filterTwo.execute(q);
            } else {
                //process initial start list for all boat classes  OK
                return list.stream()
                        .sorted(Comparator.comparing(RaceResultList::getBib).reversed())
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}