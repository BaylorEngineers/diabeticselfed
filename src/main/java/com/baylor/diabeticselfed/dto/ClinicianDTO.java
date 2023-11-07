package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class ClinicianDTO {
    private Integer userId;
    private Integer clinicianId;
    private String name;
    private String email;
}