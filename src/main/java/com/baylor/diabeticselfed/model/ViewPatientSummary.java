package com.baylor.diabeticselfed.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ViewPatientSummary {
    //patient's personal information
    private int patientId;
    private String patientName;
    private Date patientDOB;

    //patient's suvey and answer
    private List<Boolean> answerList;
    private List<Date> answerDateList;
    private List<String> questionDescriptionList;

    //patient's BMI weight track
    private int height;
    private List<Integer> weightList;
    private List<Date> weightTimeList;
    private List<Double> bmiList;

    //patient's module inform
    private List<String> moduleNames;
    private List<String> moduleDescriptions;
    private List<Integer> moduleProgressPercentages;
    private List<LocalDateTime> moduleStartTimes;
    private List<LocalDateTime> moduleEndTimes;

    //clinician add note into patient's history
    private List<String> clinicianNotes;
    private List<LocalDateTime> noteTimes;
}