package com.initex.canoe.controller;

import com.initex.canoe.domain.Event;
import com.initex.canoe.dto.EventDTO;
import com.initex.canoe.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventController {

    private EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> getEvents() {
        return service.getEvents();
    }

    @PostMapping
    @ResponseBody
    public EventDTO addEvent(@RequestBody final EventDTO eventDTO) {
        return service.addEvent(eventDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public EventDTO updateEvent(@PathVariable final String id, @RequestBody final EventDTO eventDTO) {
        return service.updateEvent(id, eventDTO);
    }
}
