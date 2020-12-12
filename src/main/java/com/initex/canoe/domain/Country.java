package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "COUNTRY")
public class Country {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COUNTRY")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String countryName) {
        this.country = countryName;
    }
}
