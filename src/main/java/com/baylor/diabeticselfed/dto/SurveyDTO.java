package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class SurveyDTO {
    private Integer patientId;
    private String dateT;
    private Integer questionId;
    private Boolean response;
}
