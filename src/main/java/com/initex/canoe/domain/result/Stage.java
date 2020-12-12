package com.initex.canoe.domain.result;

import com.initex.canoe.domain.EventRegistry;

import java.math.BigDecimal;
import java.util.Arrays;

public class Stage {

    private int[] penaltyList = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int penaltiesSum = 0;
    private BigDecimal raceTime = new BigDecimal(0);
    private boolean dsqr = false;
    private BigDecimal total = new BigDecimal(9999);     //Default 9999=DNS
    private int rank = 0;
    private boolean qualified = false;
    private EventRegistry eventRegistry;

    public int[] getPenaltyList() {
        return penaltyList;
    }

    public void setPenaltyList(int[] penaltyList) {
        this.penaltyList = penaltyList;
    }

    public int getPenaltiesSum() {
        return penaltiesSum;
    }

    public void setPenaltiesSum(int penaltiesSum) {
        this.penaltiesSum = penaltiesSum;
    }

    public BigDecimal getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(BigDecimal raceTime) {
        this.raceTime = raceTime;
    }

    public boolean isDsqr() {
        return dsqr;
    }

    public void setDsqr(boolean dsqr) {
        this.dsqr = dsqr;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isQualified() {
        return qualified;
    }

    public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public void setEventRegistry(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "penaltyList=" + Arrays.toString(penaltyList) +
                ", penaltiesSum=" + penaltiesSum +
                ", raceTime=" + raceTime +
                ", dsqr=" + dsqr +
                ", total=" + total +
                ", rank=" + rank +
                ", qualified=" + qualified +
                ", eventRegistry=" + eventRegistry +
                '}';
    }
}
