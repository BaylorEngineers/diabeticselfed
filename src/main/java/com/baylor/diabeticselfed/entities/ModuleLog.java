package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "module_log")
public class ModuleLog {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_progress_id")
    private ModuleProgress moduleProgress;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startT;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endT;

    @AssertTrue(message = "End time must be later than start time")
    public boolean isEndTimeValid() {
        if (startT == null || endT == null) {
            return true; // Allow null values to be processed by other validation rules
        }
        return endT.isAfter(startT);
    }
}
