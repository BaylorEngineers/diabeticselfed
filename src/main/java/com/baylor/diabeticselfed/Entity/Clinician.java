package com.baylor.diabeticselfed.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clinicians")
public class Clinician extends User {

    private String name;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
