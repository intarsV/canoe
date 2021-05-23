package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "MCU_DATA")
public class McuData {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BIB")
    private int bib;

    @Column(name = "TIME_STAMP")
    private long timeStamp;

    @Column(name = "UNIT_ID")
    private int unitId;

    @Column(name = "SUB_EVENT")
    private int subEvent;

    @Column(name = "DONE", columnDefinition = "TINYINT", length = 1)
    private boolean done;

    @Column(name = "DISABLED", columnDefinition = "TINYINT", length = 1)
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
