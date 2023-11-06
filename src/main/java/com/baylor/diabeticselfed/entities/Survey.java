package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    private Date dateT;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    private Boolean answer;

}
