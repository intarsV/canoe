package com.initex.canoe.controller;

import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.services.EventFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventFormats")
public class EventFormatController {

    private EventFormatService service;

    @Autowired
    public EventFormatController(EventFormatService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventFormat> getEventFormats() {
        return service.getEventFormats();
    }

}
