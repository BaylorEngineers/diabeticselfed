package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "patients")
public class Patient extends User {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int age;
    private String levelOfEd;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TYPE1, TYPE2, TYPE3
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLevelOfEd() {
        return levelOfEd;
    }

    public void setLevelOfEd(String levelOfEd) {
        this.levelOfEd = levelOfEd;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
