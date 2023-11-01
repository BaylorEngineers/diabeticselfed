package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "patient")
//@DiscriminatorValue("PATIENT")
public class Patient extends User{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;



    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private Date DOB;
    private String email;
    @Enumerated(EnumType.STRING)
    private EducationLevel levelOfEd;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User patientUser;


//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference(value = "patient-forumpost")
//    @JsonIgnore
//    private List<ForumPost> forumPosts;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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
