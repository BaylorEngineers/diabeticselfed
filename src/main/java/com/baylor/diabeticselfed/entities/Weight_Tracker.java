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
@Table(name = "weight_tracker")
public class Weight_Tracker {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private LocalDateTime date;
    private int height;
    private int weight;
}
