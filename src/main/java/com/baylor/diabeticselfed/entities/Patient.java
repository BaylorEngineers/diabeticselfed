package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "patient")
public class Patient extends User{

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
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
