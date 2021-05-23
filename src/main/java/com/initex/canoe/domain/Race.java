package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "RACE")
public class Race {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "START_TIME", columnDefinition = "DOUBLE")
    private long startTime;

    @Column(name = "FINISH_TIME", columnDefinition = "DOUBLE")
    private long finishTime;

    @Column(name = "G1")
    private int g1;

    @Column(name = "G2")
    private int g2;

    @Column(name = "G3")
    private int g3;

    @Column(name = "G4")
    private int g4;

    @Column(name = "G5")
    private int g5;

    @Column(name = "G6")
    private int g6;

    @Column(name = "G7")
    private int g7;

    @Column(name = "G8")
    private int g8;

    @Column(name = "G9")
    private int g9;

    @Column(name = "G10")
    private int g10;

    @Column(name = "G11")
    private int g11;

    @Column(name = "G12")
    private int g12;

    @Column(name = "G13")
    private int g13;

    @Column(name = "G14")
    private int g14;

    @Column(name = "G15")
    private int g15;

    @Column(name = "G16")
    private int g16;

    @Column(name = "G17")
    private int g17;

    @Column(name = "G18")
    private int g18;

    @Column(name = "G19")
    private int g19;

    @Column(name = "G20")
    private int g20;

    @Column(name = "G21")
    private int g21;

    @Column(name = "G22")
    private int g22;

    @Column(name = "G23")
    private int g23;

    @Column(name = "G24")
    private int g24;

    @ManyToOne
    @JoinColumn(name = "EVENT_REGISTRY_ID")
    private EventRegistry eventRegistry;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "SUB_EVENT_ID")
    private SubEvent subEvent;

    @Column(name = "DSQR", columnDefinition = "TINYINT", length = 1)
    private boolean dsqr;

    @Column(name = "DONE", columnDefinition = "TINYINT", length = 1)
    private boolean done;

    @Column(name = "TEAM_MODE", columnDefinition = "TINYINT", length = 1)
    private boolean teamMode;

    @Column(name = "DISABLED", columnDefinition = "TINYINT", length = 1)
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getG1() {
        return g1;
    }

    public void setG1(int g1) {
        this.g1 = g1;
    }

    public int getG2() {
        return g2;
    }

    public void setG2(int g2) {
        this.g2 = g2;
    }

    public int getG3() {
        return g3;
    }

    public void setG3(int g3) {
        this.g3 = g3;
    }

    public int getG4() {
        return g4;
    }

    public void setG4(int g4) {
        this.g4 = g4;
    }

    public int getG5() {
        return g5;
    }

    public void setG5(int g5) {
        this.g5 = g5;
    }

    public int getG6() {
        return g6;
    }

    public void setG6(int g6) {
        this.g6 = g6;
    }

    public int getG7() {
        return g7;
    }

    public void setG7(int g7) {
        this.g7 = g7;
    }

    public int getG8() {
        return g8;
    }

    public void setG8(int g8) {
        this.g8 = g8;
    }

    public int getG9() {
        return g9;
    }

    public void setG9(int g9) {
        this.g9 = g9;
    }

    public int getG10() {
        return g10;
    }

    public void setG10(int g10) {
        this.g10 = g10;
    }

    public int getG11() {
        return g11;
    }

    public void setG11(int g11) {
        this.g11 = g11;
    }

    public int getG12() {
        return g12;
    }

    public void setG12(int g12) {
        this.g12 = g12;
    }

    public int getG13() {
        return g13;
    }

    public void setG13(int g13) {
        this.g13 = g13;
    }

    public int getG14() {
        return g14;
    }

    public void setG14(int g14) {
        this.g14 = g14;
    }

    public int getG15() {
        return g15;
    }

    public void setG15(int g15) {
        this.g15 = g15;
    }

    public int getG16() {
        return g16;
    }

    public void setG16(int g16) {
        this.g16 = g16;
    }

    public int getG17() {
        return g17;
    }

    public void setG17(int g17) {
        this.g17 = g17;
    }

    public int getG18() {
        return g18;
    }

    public void setG18(int g18) {
        this.g18 = g18;
    }

    public int getG19() {
        return g19;
    }

    public void setG19(int g19) {
        this.g19 = g19;
    }

    public int getG20() {
        return g20;
    }

    public void setG20(int g20) {
        this.g20 = g20;
    }

    public int getG21() {
        return g21;
    }

    public void setG21(int g21) {
        this.g21 = g21;
    }

    public int getG22() {
        return g22;
    }

    public void setG22(int g22) {
        this.g22 = g22;
    }

    public int getG23() {
        return g23;
    }

    public void setG23(int g23) {
        this.g23 = g23;
    }

    public int getG24() {
        return g24;
    }

    public void setG24(int g24) {
        this.g24 = g24;
    }

    public int[] getPenaltyList() {
        return new int[]{this.g1, this.g2, this.g3, this.g4, this.g5, this.g6, this.g7, this.g8, this.g9, this.g10
                , this.g11, this.g12, this.g13, this.g14, this.g15, this.g16, this.g17, this.g18, this.g19, this.g20
                , this.g21, this.g22, this.g23, this.g24
        };
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public void setEventRegistry(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public SubEvent getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(SubEvent subEvent) {
        this.subEvent = subEvent;
    }

    public boolean isDsqr() {
        return dsqr;
    }

    public void setDsqr(boolean dsqr) {
        this.dsqr = dsqr;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
