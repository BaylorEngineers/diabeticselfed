package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "patient")
public class Patient extends User {

    private String name;
    private Date DOB;
    @Enumerated(EnumType.STRING)
    private EducationLevel levelOfEd;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TYPE1, TYPE2, TYPE3
    }
    public enum EducationLevel {
        OTHER,
        HIGHSCHOOL,
        UNDERGRADUATE,
        GRADUATE
    }
}
