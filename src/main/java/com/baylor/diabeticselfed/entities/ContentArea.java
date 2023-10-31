package com.baylor.diabeticselfed.entities;

import jakarta.persistence.*;
import lombok.Data;

import com.baylor.diabeticselfed.token.Token;

import java.util.List;

@Entity
@Data
@Table(name = "content_area")
public class ContentArea {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "ContentArea")
    private List<Module> modules;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
