package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "module_log")
public class Module_Log {

    private int patientId;
    private LocalDateTime start;
    private LocalDateTime end;

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public LocalDateTime getStart() { return start; }

    public void setStart(LocalDateTime start) { this.start = start; }

    public LocalDateTime getEnd() { return end; }

    public void setEnd(LocalDateTime end) { this.end = end; }
}
