package com.initex.canoe.dto;

public class RaceConfigQuery {

    long eventId;

    public RaceConfigQuery(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
