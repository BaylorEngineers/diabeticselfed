package com.baylor.diabeticselfed.auth;


import com.baylor.diabeticselfed.dto.InvitationDto;
import com.baylor.diabeticselfed.entities.Invitation;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.repository.InvitationRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {
  @Autowired
  private UserRepository userRepository;
  private final AuthenticationService service;
  @Autowired
  private final InvitationRepository invitationRepository;


  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/invite")
  public ResponseEntity<?> inviteUser(@RequestBody InvitationDto invitationDto) {
    System.out.println(invitationDto.getEmail());
    System.out.println(invitationDto.getRole());
    if (userRepository.findByEmail(invitationDto.getEmail()).isPresent()) {
      System.out.println("Email is already registered");
      return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
    }
    Invitation invitation = service.createInvitation(invitationDto.getEmail(), Role.valueOf(invitationDto.getRole()));
    return new ResponseEntity<>(invitation, HttpStatus.CREATED);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    }

    Optional<Invitation> optionalInvitation = Optional.ofNullable(invitationRepository.findByToken(request.getToken()));
    if (optionalInvitation.isEmpty()) {
      return new ResponseEntity<>("Invalid invitation token", HttpStatus.BAD_REQUEST);
    }

    Invitation invitation = optionalInvitation.get();
    if (invitation.isUsed() || invitation.getExpiryDate().isBefore(LocalDateTime.now())) {
      return new ResponseEntity<>("Invitation token is expired or already used", HttpStatus.BAD_REQUEST);
    }

    request.setRole(invitation.getRole());
    AuthenticationResponse response = service.register(request);
    invitation.setUsed(true);
    invitationRepository.save(invitation);

    return ResponseEntity.ok(response);
  }


//
//  @PostMapping("/register")
//  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
//    // Check if the email already exists in the database
//    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//      return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
//    }
//    AuthenticationResponse response = service.register(request);
//    return ResponseEntity.ok(response);
//  }

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
