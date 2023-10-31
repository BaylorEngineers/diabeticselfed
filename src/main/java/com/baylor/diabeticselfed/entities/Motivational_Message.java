package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "motivation_message")
public class Motivational_Message {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private LocalDateTime date;
    private String message_content;

    public Integer getId() {
        return id;
    }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public String getMessage_content() { return message_content; }

    public void setMessage_content(String message_content) { this.message_content = message_content; }





}
