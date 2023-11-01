package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "weight_tracker")
public class WeightTracker {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer patientId;
    private Date dateT;
    private Integer height;
    private Integer weight;
}
