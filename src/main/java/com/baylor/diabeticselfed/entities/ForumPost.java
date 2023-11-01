package com.baylor.diabeticselfed.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "forumpost")
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonBackReference(value = "patient-forumpost")
    @JsonIgnore
    private Patient patient;

    private String title;

    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

//    @Lob
//    @Basic(fetch = FetchType.EAGER)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
}
