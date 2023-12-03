package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PatientDTO {
    private Integer userId;
    private Integer patientId;
    private String name;
    private String email;
}
