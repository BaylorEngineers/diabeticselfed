package com.baylor.diabeticselfed.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clinicians")
public class Clinician extends User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
