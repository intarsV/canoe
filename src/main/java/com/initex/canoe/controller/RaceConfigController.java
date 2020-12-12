package com.initex.canoe.controller;

import com.initex.canoe.domain.RaceConfig;
import com.initex.canoe.dto.RaceConfigDTO;
import com.initex.canoe.dto.RaceConfigQuery;
import com.initex.canoe.services.RaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/race-config")
public class RaceConfigController {

    private RaceConfigService service;

    @Autowired
    public RaceConfigController(RaceConfigService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<RaceConfig> getRaceConfig(final RaceConfigQuery query) {
        return service.getList(query);
    }

    @PostMapping
    @ResponseBody
    public RaceConfigDTO addRaceConfig(@RequestBody final RaceConfigDTO raceConfigDTO) {
        return service.addRaceConfig(raceConfigDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public RaceConfigDTO updateRaceConfig(@PathVariable final String id, @RequestBody final RaceConfigDTO raceConfigDTO) {
        return service.updateRaceConfig(id, raceConfigDTO);
    }
}
