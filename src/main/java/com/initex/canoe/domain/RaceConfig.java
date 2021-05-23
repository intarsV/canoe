package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "RACE_CONFIG")
public class RaceConfig {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "BOAT_CLASS_ID")
    private BoatClass boatClass;

    @Column(name = "HEAT_1")
    private int heat1;

    @Column(name = "HEAT_2")
    private int heat2;

    @Column(name = "SEMI_FINAL")
    private int semiFinal;

    public int[] getConfigList() {
        return new int[]{heat1, heat2, semiFinal};
    }

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

    public BoatClass getBoatClass() {
        return boatClass;
    }

    public void setBoatClass(BoatClass boatClass) {
        this.boatClass = boatClass;
    }

    public int getHeat1() {
        return heat1;
    }

    public void setHeat1(int heat1) {
        this.heat1 = heat1;
    }

    public int getHeat2() {
        return heat2;
    }

    public void setHeat2(int heat2) {
        this.heat2 = heat2;
    }

    public int getSemiFinal() {
        return semiFinal;
    }

    public void setSemiFinal(int semiFinal) {
        this.semiFinal = semiFinal;
    }
}
