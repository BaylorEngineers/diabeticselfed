package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "weight_tracker")
public class Weight_Tracker {

    private int patientId;
    private LocalDateTime date;
    private int height;
    private int weight;

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = (height > 0) ? height : this.height; }

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = (weight > 0) ? weight : this.weight; }
}
