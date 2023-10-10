package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "module_progress")
public class Module_Progress {

    private int patientId;
    private Boolean[] progress;

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public Boolean[] getProgress() { return progress; }

    public void setProgress(Boolean[] progress) { this.progress = progress; }
}
