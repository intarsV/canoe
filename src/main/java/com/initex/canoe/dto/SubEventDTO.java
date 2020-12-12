package com.initex.canoe.dto;

import com.initex.canoe.domain.EventFormat;

public class SubEventDTO {

    private Long id;
    private EventFormat eventFormat;
    private String subEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventFormat getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(EventFormat eventFormat) {
        this.eventFormat = eventFormat;
    }

    public String getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(String subEvent) {
        this.subEvent = subEvent;
    }
}
