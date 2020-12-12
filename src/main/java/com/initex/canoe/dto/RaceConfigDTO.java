package com.initex.canoe.dto;

import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.domain.Event;


public class RaceConfigDTO {

    private Long id;
    private Event event;
    private BoatClass boatClass;
    private int heat1;
    private int heat2;
    private int semiFinal;

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
