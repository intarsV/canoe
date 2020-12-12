package com.initex.canoe.controller;

import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.SubEventDTO;
import com.initex.canoe.services.SubEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sub-events")
public class SubEventController {

    private SubEventService service;

    @Autowired
    public SubEventController(SubEventService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<SubEvent> getSubEvent() {
        return service.getSubEvent();
    }

    @PostMapping
    @ResponseBody
    public SubEventDTO addSubEvent(@RequestBody final SubEventDTO subEventDTO) {
        return service.addSubEvent(subEventDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public SubEventDTO updateSubEvent(@PathVariable final String id, @RequestBody final SubEventDTO subEventDTO) {
        return service.updateSubEvent(id, subEventDTO);
    }
}
