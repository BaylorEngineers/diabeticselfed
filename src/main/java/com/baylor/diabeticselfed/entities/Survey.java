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
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private LocalDateTime date;
    private int questionId;
    private boolean answer;
}
