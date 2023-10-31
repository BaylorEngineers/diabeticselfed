package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "forum_post")
public class ForumPost {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private String title;
    private String content;
    private LocalDateTime time_stamp;
}
