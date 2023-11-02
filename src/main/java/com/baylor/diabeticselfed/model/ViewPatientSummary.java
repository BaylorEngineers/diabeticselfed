package com.baylor.diabeticselfed.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class ViewPatientSummary {

    @Id
    private Integer id;
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