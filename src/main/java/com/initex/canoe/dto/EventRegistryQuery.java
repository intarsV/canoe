package com.initex.canoe.dto;

public class EventRegistryQuery {

    private Long id;
    private Long eventId;
    private Long competitorId;
    private String teamMates;
    private Long groupId;
    private Long boatClassId;
    private int bib;
    private boolean teamMode;
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorId) {
        this.competitorId = competitorId;
    }

    public String getTeamMates() {
        return teamMates;
    }

    public void setTeamMates(String teamMates) {
        this.teamMates = teamMates;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getBoatClassId() {
        return boatClassId;
    }

    public void setBoatClassId(Long boatClassId) {
        this.boatClassId = boatClassId;
    }

    public int getBib() {
        return bib;
    }

    public void setBib(int bib) {
        this.bib = bib;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
