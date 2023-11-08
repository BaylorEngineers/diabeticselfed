package com.baylor.diabeticselfed.dto;

import com.baylor.diabeticselfed.entities.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}