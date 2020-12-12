package com.initex.canoe.dto;

public class ResultQuery {

    private Long eventName;
    private int eventFormat;
    private String subEvent;
    private String boatClass;
    private boolean teamMode;
    private String reportType;

    public Long getEventName() {
        return eventName;
    }

    public void setEventName(Long eventName) {
        this.eventName = eventName;
    }

    public int getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(int eventFormat) {
        this.eventFormat = eventFormat;
    }

    public String getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(String subEvent) {
        this.subEvent = subEvent;
    }

    public String getBoatClass() {
        return boatClass;
    }

    public void setBoatClass(String boatClass) {
        this.boatClass = boatClass;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "ResultQuery{" +
                "event=" + eventName +
                ", eventFormat=" + eventFormat +
                ", subEvent='" + subEvent + '\'' +
                ", boatClass='" + boatClass + '\'' +
                ", teamMode=" + teamMode +
                ", reportType='" + reportType + '\'' +
                '}';
    }
}