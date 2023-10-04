package com.baylor.diabeticselfed.Entity;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "admins")
public class Admin extends User {

    private String name;

}
