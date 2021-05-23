package com.initex.canoe.domain.result;

import com.initex.canoe.domain.EventRegistry;

import java.math.BigDecimal;

public class RaceStageResult {
    private EventRegistry eventRegistry;
    private int bib;

    private String stageName;
    private int stageIndex;
    private int boatClassIndex;

    private Stage stage;
    private BigDecimal bestH1H2 = new BigDecimal(9999);             //Default 9999=DNS
    private BigDecimal sumH1H2 = new BigDecimal(9999);              //Default 9999=DNS

    public RaceStageResult(EventRegistry eventRegistry, int stageIndex, int boatClassIndex) {
        this.eventRegistry = eventRegistry;
        this.stageIndex = stageIndex;
        this.boatClassIndex = boatClassIndex;
    }

    public RaceStageResult(EventRegistry eventRegistry, int stageIndex, int boatClassIndex, Stage stage) {
        this.eventRegistry = eventRegistry;
        this.stageIndex = stageIndex;
        this.boatClassIndex = boatClassIndex;
        this.stage = stage;
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public void setEventRegistry(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    public int getBib() {
        return bib;
    }

    public void setBib(int bib) {
        this.bib = bib;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getStageIndex() {
        return stageIndex;
    }

    public void setStageIndex(int stageIndex) {
        this.stageIndex = stageIndex;
    }

    public int getBoatClassIndex() {
        return boatClassIndex;
    }

    public void setBoatClassIndex(int boatClassIndex) {
        this.boatClassIndex = boatClassIndex;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public BigDecimal getBestH1H2() {
        return bestH1H2;
    }

    public void setBestH1H2(BigDecimal bestH1H2) {
        this.bestH1H2 = bestH1H2;
    }

    public BigDecimal getSumH1H2() {
        return sumH1H2;
    }

    public void setSumH1H2(BigDecimal sumH1H2) {
        this.sumH1H2 = sumH1H2;
    }

    @Override
    public String toString() {
        return "RaceStageResult{" +
                "eventRegistry=" + eventRegistry +
                ", bib=" + bib +
                ", stageIndex=" + stageIndex +
                ", boatClassIndex=" + boatClassIndex +
                ", stage=" + stage +
                ", bestH1H2=" + bestH1H2 +
                ", sumH1H2=" + sumH1H2 +
                '}';
    }
}
