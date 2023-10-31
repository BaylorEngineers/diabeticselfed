package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;

    private Integer weightloss_percent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Integer getWeightloss_percent() {
        return weightloss_percent;
    }

    public void setWeightloss_percent(Integer weightloss_percent) {
        this.weightloss_percent = weightloss_percent;
    }
}
