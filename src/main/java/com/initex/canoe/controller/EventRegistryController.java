package com.initex.canoe.controller;

import com.initex.canoe.domain.EventRegistry;
import com.initex.canoe.dto.EventRegistryDTO;
import com.initex.canoe.dto.EventRegistryQuery;
import com.initex.canoe.services.EventRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/eventRegistry")
public class EventRegistryController {

    private EventRegistryService service;

    @Autowired
    public EventRegistryController(EventRegistryService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<EventRegistry> getEventRegistry(final EventRegistryQuery query) {
        return service.getEventRegistry(query);
    }

    @PostMapping
    @ResponseBody
    public EventRegistryDTO addEventRegistry(@RequestBody final EventRegistryDTO eventRegistryDTO) {
        return service.addEventRegistry(eventRegistryDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public EventRegistryDTO updateEventRegistry(@PathVariable final String id, @RequestBody final EventRegistryDTO eventRegistryDTO) {
        return service.updateEventRegistry(id, eventRegistryDTO);
    }
}

