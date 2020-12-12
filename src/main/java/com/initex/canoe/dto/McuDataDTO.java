package com.initex.canoe.dto;

public class McuDataDTO {

    private Long id;
    private int bib;
    private long timeStamp;
    private int unitId;
    private boolean done;
    private boolean disabled;
    private long eventId;
    private int subEventId;
    private boolean teamMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBib() {
        return bib;
    }

    public void setBib(int bib) {
        this.bib = bib;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getSubEventId() {
        return subEventId;
    }

    public void setSubEventId(int subEventId) {
        this.subEventId = subEventId;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    @Override
    public String toString() {
        return "McuDataAcceptDTO{" +
                "id=" + id +
                ", bib=" + bib +
                ", timeStamp=" + timeStamp +
                ", unitId=" + unitId +
                ", done=" + done +
                ", disabled=" + disabled +
                ", eventId=" + eventId +
                ", subEventId=" + subEventId +
                ", teamMode=" + teamMode +
                '}';
    }
}
