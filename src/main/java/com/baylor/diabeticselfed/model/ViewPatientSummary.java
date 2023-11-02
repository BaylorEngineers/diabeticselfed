package com.baylor.diabeticselfed.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewPatientSummary {
    private int patientId;
    private int moduleId;
    private int completedPercentage;
    private LocalDateTime startT;
    private LocalDateTime endT;
    private int height;
    private int weight;
    private double bmi;
    private LocalDateTime surveyDate;
    private boolean surveyAnswer;
    private String questionDescription;
}