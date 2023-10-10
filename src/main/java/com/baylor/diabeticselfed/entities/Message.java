package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "message")
public class Message {

    private int senderId;
    private int recipientId;
    private LocalDateTime date;
    private String subject;
    private String content;

    public int getSenderId() { return senderId; }

    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getRecipientId() { return recipientId; }

    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
