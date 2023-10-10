package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "forum_post")
public class Forum_Post {

    private int postId;
    private int patientId;
    private String title;
    private String content;
    private LocalDateTime time_stamp;

    public int getPostId() { return postId; }

    public void setPostId(int postId) { this.postId = postId; }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTime_stamp() { return time_stamp; }

    public void setTime_stamp(LocalDateTime time_stamp) { this.time_stamp = time_stamp; }
}
