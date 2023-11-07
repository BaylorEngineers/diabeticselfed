package com.baylor.diabeticselfed.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "clinician_notes")
public class ClinicianNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private int patientId;

    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
