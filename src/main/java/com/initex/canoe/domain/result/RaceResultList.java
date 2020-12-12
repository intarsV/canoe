package com.initex.canoe.domain.result;

import com.initex.canoe.domain.EventRegistry;

import java.math.BigDecimal;

public class RaceResultList {

    private EventRegistry eventRegistry;
    private int bib;

    private Stage heatOneStage;
    private Stage heatTwoStage;
    private Stage semiFinalStage;
    private Stage finalStage;

    private BigDecimal totalOfH1H2 = new BigDecimal(9999);            //Default 9999=DNS
    private int rankOfH1H2 = 0;

    public RaceResultList(EventRegistry eventRegistry, int bib) {
        this.eventRegistry = eventRegistry;
        this.bib = bib;
    }

    public RaceResultList(EventRegistry eventRegistry, int bib, Stage heatOneStage, Stage heatTwoStage, Stage semiFinalStage, Stage finalStage) {
        this.eventRegistry = eventRegistry;
        this.bib = bib;
        this.heatOneStage = heatOneStage;
        this.heatTwoStage = heatTwoStage;
        this.semiFinalStage = semiFinalStage;
        this.finalStage = finalStage;
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public int getBib() {
        return bib;
    }

    public void setBib(int bib) {
        this.bib = bib;
    }

    public void setEventRegistry(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    public Stage getHeatOneStage() {
        return heatOneStage;
    }

    public void setHeatOneStage(Stage heatOneStage) {
        this.heatOneStage = heatOneStage;
    }

    public Stage getHeatTwoStage() {
        return heatTwoStage;
    }

    public void setHeatTwoStage(Stage heatTwoStage) {
        this.heatTwoStage = heatTwoStage;
    }

    public Stage getSemiFinalStage() {
        return semiFinalStage;
    }

    public void setSemiFinalStage(Stage semiFinalStage) {
        this.semiFinalStage = semiFinalStage;
    }

    public Stage getFinalStage() {
        return finalStage;
    }

    public void setFinalStage(Stage finalStage) {
        this.finalStage = finalStage;
    }

    public BigDecimal getTotalOfH1H2() {
        return totalOfH1H2;
    }

    public void setTotalOfH1H2(BigDecimal totalOfH1H2) {
        this.totalOfH1H2 = totalOfH1H2;
    }

    public int getRankOfH1H2() {
        return rankOfH1H2;
    }

    public void setRankOfH1H2(int rankOfH1H2) {
        this.rankOfH1H2 = rankOfH1H2;
    }

    @Override
    public String toString() {
        return "RaceResultList{" +
                "eventRegistry=" + eventRegistry +
                ", bib=" + bib +
                ", heatOneStage=" + heatOneStage +
                ", heatTwoStage=" + heatTwoStage +
                ", semiFinalStage=" + semiFinalStage +
                ", finalStage=" + finalStage +
                ", totalOfH1H2=" + totalOfH1H2 +
                ", ranOfH1H2=" + rankOfH1H2 +
                '}';
    }
}
