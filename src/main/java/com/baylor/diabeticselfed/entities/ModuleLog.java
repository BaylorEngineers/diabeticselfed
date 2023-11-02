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

    private Date startT;
    private Date endT;
}
