package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "admins")
public class Admin extends User {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;

}
