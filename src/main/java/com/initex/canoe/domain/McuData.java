package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "MCU_DATA")
public class McuData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bib")
    private int bib;

    @Column(name = "time_stamp")
    private long timeStamp;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "sub_event")
    private int subEvent;

    @Column(name = "done", columnDefinition = "TINYINT", length = 1)
    private boolean done;

    @Column(name = "disabled", columnDefinition = "TINYINT", length = 1)
    private boolean disabled;

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

    public int getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(int subEvent) {
        this.subEvent = subEvent;
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
