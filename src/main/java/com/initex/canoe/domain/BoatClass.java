package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOAT_CLASS")
public class BoatClass {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOAT_CLASS")
    private String boatClass;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoatClass() {
        return boatClass;
    }

    public void setBoatClass(String boatClass) {
        this.boatClass = boatClass;
    }
}
