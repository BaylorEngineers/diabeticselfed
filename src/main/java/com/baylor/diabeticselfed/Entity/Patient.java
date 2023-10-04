package com.baylor.diabeticselfed.Entity;
import jakarta.persistence.*;
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
