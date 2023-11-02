package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "module_progress")
public class ModuleProgress {

    @Id
    @GeneratedValue
    private Integer id;
    private int patientId;
    private int moduleId;
    private int completed_percentage;

}
