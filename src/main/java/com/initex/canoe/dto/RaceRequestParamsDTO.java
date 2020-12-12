package com.initex.canoe.dto;

public class RaceRequestParamsDTO {

    private long eventId;
    private long subEventId;
    private boolean teamMode;
    private boolean done;
    private boolean disabled;

    public RaceRequestParamsDTO() {
    }

    public RaceRequestParamsDTO(long eventId, long subEventId, boolean teamMode, boolean done, boolean disabled) {
        this.eventId = eventId;
        this.subEventId = subEventId;
        this.teamMode = teamMode;
        this.done = done;
        this.disabled = disabled;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getSubEventId() {
        return subEventId;
    }

    public void setSubEventId(long subEventId) {
        this.subEventId = subEventId;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
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
}
