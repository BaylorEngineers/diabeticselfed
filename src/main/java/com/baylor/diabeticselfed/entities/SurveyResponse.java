package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "survey_response")
public class SurveyResponse {

    @Id
    @GeneratedValue
    private Integer id;

    private int patientId;
    private boolean submitted;

    public Integer getId() {
        return id;
    }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public boolean isSubmitted() { return submitted; }

    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
}
