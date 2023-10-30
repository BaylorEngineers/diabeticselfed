package com.baylor.diabeticselfed.auth;

import com.baylor.diabeticselfed.entities.Invitation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/invite")
  @CrossOrigin(origins = "http://localhost:3000")
  public Invitation inviteUser(@RequestParam @Email String email) {
    return service.createInvitation(email);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

//  @PostMapping("/login")
//  public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest loginRequest) {
//    User user = userService.findByUsername(loginRequest.getEmail());
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    boolean isPasswordMatch = encoder.matches(loginRequest.getPassword(), user.getPassword());
//
//    if (user != null && isPasswordMatch) {
//      return ResponseEntity.ok(user.getRole());
//    } else {
//      return ResponseEntity.badRequest().body("Invalid username or password");
//    }
//  }



}
