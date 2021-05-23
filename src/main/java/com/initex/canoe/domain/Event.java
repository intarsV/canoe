package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "EVENT")
public class Event {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @ManyToOne
    @JoinColumn(name = "EVENT_FORMAT_ID")
    private EventFormat eventFormat;

    @Column(name = "PLACE_DATE")
    private String placeDate;

    @Column(name = "DISABLED", columnDefinition = "TINYINT", length = 1)
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventFormat getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(EventFormat eventFormat) {
        this.eventFormat = eventFormat;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(String placeDate) {
        this.placeDate = placeDate;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
