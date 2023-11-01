package com.baylor.diabeticselfed.auth;


import com.baylor.diabeticselfed.dto.InvitationDto;
import com.baylor.diabeticselfed.entities.Invitation;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
@Autowired
private UserRepository userRepository;
  private final AuthenticationService service;

  @PostMapping("/invite")
  @CrossOrigin(origins = "http://localhost:3000")
  public ResponseEntity<Invitation> inviteUser(@RequestBody InvitationDto invitationDto) {
    Invitation invitation = service.createInvitation(invitationDto.getEmail(), Role.valueOf(invitationDto.getRole()));
    return new ResponseEntity<>(invitation, HttpStatus.CREATED);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    // Check if the email already exists in the database
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    }
    AuthenticationResponse response = service.register(request);
    return ResponseEntity.ok(response);
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
