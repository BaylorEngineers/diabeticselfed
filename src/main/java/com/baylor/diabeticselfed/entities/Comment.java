package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
public class Comment {

    private int commentId;
    private int patientId;
    private int postId;
    private String text;
    private LocalDateTime time_stamp;

    public int getCommentId() { return commentId; }

    public void setCommentId(int commentId) { this.commentId = commentId; }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getPostId() { return postId; }

    public void setPostId(int postId) { this.postId = postId; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public LocalDateTime getTime_stamp() { return time_stamp; }

    public void setTime_stamp(LocalDateTime time_stamp) { this.time_stamp = time_stamp; }
}
