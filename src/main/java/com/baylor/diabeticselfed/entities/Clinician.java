package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clinicians")
//@DiscriminatorValue("CLINICIAN")

public class Clinician extends User {
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User clinicianUser;

    private String name;


}
