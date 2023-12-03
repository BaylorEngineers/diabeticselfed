package com.baylor.diabeticselfed.dto;

import com.baylor.diabeticselfed.entities.Patient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.security.PrivateKey;
import java.util.Date;

@Data
public class PatientProfileDTO {

    private Integer patientId;
    private String name;
    private Date dob;
    private String education;
    private String type;
}
