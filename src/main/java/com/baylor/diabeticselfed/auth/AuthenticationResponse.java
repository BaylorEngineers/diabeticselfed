package com.baylor.diabeticselfed.auth;

import com.baylor.diabeticselfed.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("role")
  private Role role;
  @JsonProperty("userID")
  private Integer userID;
  @JsonProperty("patientID")
  private Integer patientID;
  @JsonProperty("clinicianID")
  private Integer clinicianID;
  @JsonProperty("showSurvey")
  private boolean showSurvey;

}
