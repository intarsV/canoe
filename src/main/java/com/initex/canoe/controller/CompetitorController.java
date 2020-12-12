package com.initex.canoe.controller;

import com.initex.canoe.domain.Competitor;
import com.initex.canoe.dto.CompetitorDTO;
import com.initex.canoe.services.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/competitors")
public class CompetitorController {

    private CompetitorService service;

    @Autowired
    public CompetitorController(CompetitorService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<Competitor> getCompetitors() {
        return service.getCompetitors();
    }

    @PostMapping
    @ResponseBody
    public CompetitorDTO addCompetitor(@RequestBody final CompetitorDTO competitorDTO) {
        return service.addCompetitor(competitorDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public CompetitorDTO updateCompetitor(@PathVariable final String id, @RequestBody final CompetitorDTO competitorDTO) {
        return service.updateCompetitor(id, competitorDTO);
    }
}
