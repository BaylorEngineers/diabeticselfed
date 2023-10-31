package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "module_log")
public class Module_Log {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private LocalDateTime startT;
    private LocalDateTime endT;

    public Integer getId() {
        return id;
    }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public LocalDateTime getStart() { return startT; }

    public void setStart(LocalDateTime start) { this.startT = start; }

    public LocalDateTime getEnd() { return endT; }

    public void setEnd(LocalDateTime end) { this.endT = end; }
}
