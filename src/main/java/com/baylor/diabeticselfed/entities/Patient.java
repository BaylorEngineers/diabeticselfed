package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "patients")
public class Patient extends User {

    private String name;
    private int age;
    private String levelOfEd;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TYPE1, TYPE2, TYPE3
    }
}
