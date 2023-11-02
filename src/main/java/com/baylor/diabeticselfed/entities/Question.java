package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private String description;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Survey> survey;
}
