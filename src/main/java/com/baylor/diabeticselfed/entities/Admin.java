package com.baylor.diabeticselfed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "admins")
public class Admin extends User {

    private String name;

}
