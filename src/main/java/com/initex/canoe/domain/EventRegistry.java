package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_REGISTRY")
public class EventRegistry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "competitor_id")
    private Competitor competitor;

    @JoinColumn(name="team_mates")
    private String teamMates;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private AgeGroup ageGroup;

    @ManyToOne
    @JoinColumn(name = "boat_class_id")
    private BoatClass boatClass;

    @Column(name = "bib")
    private int bib;

    @Column(name = "team_mode", columnDefinition = "TINYINT", length = 1)
    private boolean teamMode;

    @Column(name = "disabled", columnDefinition = "TINYINT", length = 1)
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

    @Override
    public String toString() {
        return "EventRegistry{" +
                "id=" + id +
                ", event=" + event +
                ", competitor=" + competitor +
                ", teamMates='" + teamMates + '\'' +
                ", ageGroup=" + ageGroup +
                ", boatClass=" + boatClass +
                ", bib=" + bib +
                ", teamMode=" + teamMode +
                ", disabled=" + disabled +
                '}';
    }
}
