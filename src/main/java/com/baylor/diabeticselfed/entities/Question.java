package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
