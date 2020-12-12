package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "SUB_EVENT")
public class SubEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_format_id")
    private EventFormat eventFormat;

    @Column(name = "sub_event")
    private String subEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventFormat getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(EventFormat eventFormat) {
        this.eventFormat = eventFormat;
    }

    public String getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(String subEvent) {
        this.subEvent = subEvent;
    }

    @Override
    public String toString() {
        return "SubEvent{" +
                "id=" + id +
                ", eventFormat=" + eventFormat +
                ", subEvent='" + subEvent + '\'' +
                '}';
    }
}
