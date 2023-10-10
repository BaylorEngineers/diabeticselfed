package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "survey")
public class Survey {

    private int patientId;
    private LocalDateTime date;
    private int[] questionId;
    private boolean[] answer;

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public int[] getQuestionId() { return questionId; }

    public void setQuestionId(int[] questionId) { this.questionId = questionId; }

    public boolean[] getAnswer() { return answer; }

    public void setAnswer(boolean[] answer) { this.answer = answer; }
}
