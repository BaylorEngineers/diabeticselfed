package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}