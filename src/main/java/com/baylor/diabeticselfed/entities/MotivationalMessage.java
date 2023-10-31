package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "motivation_message")
public class MotivationalMessage {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private LocalDateTime date;
    private String message_content;
}
