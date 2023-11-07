package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class ClinicianNoteDTO {
    private Integer patientId;
    private String note;
}