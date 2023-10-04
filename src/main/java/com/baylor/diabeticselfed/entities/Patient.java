package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "patients")
public class Patient extends User {

    private String name;
    private int age;
    private String levelOfEd;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TYPE1, TYPE2, TYPE3
    }
}
