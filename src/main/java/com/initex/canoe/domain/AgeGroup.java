package com.initex.canoe.domain;

import javax.persistence.*;

@Entity
@Table(name = "AGE_GROUP")
public class AgeGroup {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AGE_GROUP")
    private String ageGroup;

    @Column(name = "disabled", columnDefinition = "TINYINT", length = 1)
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
