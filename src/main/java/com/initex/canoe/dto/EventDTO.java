package com.initex.canoe.dto;

import com.initex.canoe.domain.EventFormat;

public class EventDTO {

    private Long id;
    private String eventName;
    private EventFormat eventFormat;
    private String placeDate;
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventFormat getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(EventFormat eventFormat) {
        this.eventFormat = eventFormat;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(String placeDate) {
        this.placeDate = placeDate;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
