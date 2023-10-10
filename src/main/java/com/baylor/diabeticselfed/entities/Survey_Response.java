package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "survey_response")
public class Survey_Response {

    private int patientId;
    private boolean submitted;

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public boolean isSubmitted() { return submitted; }

    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
}
