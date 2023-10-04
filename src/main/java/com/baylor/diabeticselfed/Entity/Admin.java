package com.baylor.diabeticselfed.Entity;
import jakarta.persistence.*;
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
