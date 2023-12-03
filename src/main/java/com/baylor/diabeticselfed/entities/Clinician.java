package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "message-receiver")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "message-sender")
    private List<Message> sentMessages;
}