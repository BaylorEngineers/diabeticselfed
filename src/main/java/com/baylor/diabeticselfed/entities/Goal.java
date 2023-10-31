package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer patientId;
    private Integer weightLossPercent;
    private boolean isAccomplished;
}
