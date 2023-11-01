package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WeightTrackerReportDTO {

    private Integer patientId;
    private Integer height;
    private Integer weight;
    private String dateT;

}
