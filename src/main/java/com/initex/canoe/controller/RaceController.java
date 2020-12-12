package com.initex.canoe.controller;

import com.initex.canoe.dto.RaceDTO;
import com.initex.canoe.dto.RaceRequestParamsDTO;
import com.initex.canoe.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/race")
public class RaceController {

    private RaceService service;

    @Autowired
    public RaceController(RaceService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<RaceDTO> getRaceList(final RaceRequestParamsDTO params) {
        return service.getRace(params);
    }

    @PostMapping
    @ResponseBody
    public RaceDTO addRace(@RequestBody final RaceDTO raceDTO) {
        return service.addRace(raceDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public RaceDTO updateRace(@PathVariable final String id, @RequestBody final RaceDTO raceDTO) {
        return service.updateRace(id, raceDTO);
    }
}
