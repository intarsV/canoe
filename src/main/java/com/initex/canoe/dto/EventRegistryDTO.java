package com.initex.canoe.dto;

import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.domain.Competitor;
import com.initex.canoe.domain.Event;
import com.initex.canoe.domain.AgeGroup;

public class EventRegistryDTO {

    private Long id;
    private Event event;
    private Competitor competitor;
    private String teamMates;
    private AgeGroup ageGroup;
    private BoatClass boatClass;
    private int bib;
    private boolean teamMode;
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public String getTeamMates() {
        return teamMates;
    }

    public void setTeamMates(String teamMates) {
        this.teamMates = teamMates;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public BoatClass getBoatClass() {
        return boatClass;
    }

    public void setBoatClass(BoatClass boatClass) {
        this.boatClass = boatClass;
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
