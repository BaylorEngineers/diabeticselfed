package com.baylor.diabeticselfed.DTO;

import com.baylor.diabeticselfed.Entity.User;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String username;
    private String email;
    private String password;
    private User.Role role;

}
