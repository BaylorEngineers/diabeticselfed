package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "goal")
public class Goal {

    private int patientId;

    //@TODO complete goal content

}
