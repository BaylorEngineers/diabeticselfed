package com.baylor.diabeticselfed.auth;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private Role role;
  private Date DOB;
  private Patient.EducationLevel LevelofEdu;
}
