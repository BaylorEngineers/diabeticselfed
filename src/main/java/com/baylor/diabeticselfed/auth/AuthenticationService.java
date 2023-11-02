package com.baylor.diabeticselfed.auth;

import com.baylor.diabeticselfed.config.JwtService;
import com.baylor.diabeticselfed.entities.*;
import com.baylor.diabeticselfed.repository.ClinicianRepository;
import com.baylor.diabeticselfed.repository.InvitationRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.MailService;
import com.baylor.diabeticselfed.token.Token;
import com.baylor.diabeticselfed.token.TokenRepository;
import com.baylor.diabeticselfed.token.TokenType;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PatientRepository patientRepository;

  private final ClinicianRepository clinicianRepository;
  @Autowired
  private InvitationRepository invitationRepository;

  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  @Autowired
  private MailService mailService;

  @Transactional
  public Invitation createInvitation(String email, Role role) {
    Invitation invite = new Invitation();
    invite.setEmail(email);
    invite.setExpiryDate(LocalDateTime.now().plusDays(7)); // 7 days validity
    invite.setToken(UUID.randomUUID().toString());
    invite.setRole(role);
    invite.setUsed(false);

    mailService.sendInvitationEmail(invite.getEmail(), invite.getToken());

    return invitationRepository.save(invite);
  }

  public AuthenticationResponse register(RegisterRequest request) {
    System.out.println(request);
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

    var savedUser = repository.save(user);
    var temp = new Patient();
    switch (request.getRole()) {
      case PATIENT:
        Patient patient = new Patient();
        patient.setPatientUser(user);
        patient.setName(request.getFirstname()+" "+request.getLastname());
        patient.setDOB(request.getDob());
        patient.setLevelOfEd(request.getLevelofedu());
        patient.setEmail(request.getEmail());
        temp = patientRepository.save(patient);
        System.out.println(temp);
        break;
      case CLINICIAN:
        Clinician clinician = new Clinician();
        clinician.setClinicianUser(user);
        clinician.setName(request.getFirstname()+" "+request.getLastname());
        clinician.setEmail(request.getEmail());
        clinician.setFirstname(request.getFirstname());
        clinician.setLastname(request.getLastname());
        clinicianRepository.save(clinician);
        break;
    }
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      saveUserToken(savedUser, jwtToken);
      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .userID(user.getId())
              .patientID(temp.getId())
              .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    Patient temp = patientRepository.findByPatientUser(user).get();
//    System.out.println("Login:"+temp);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole())
            .userID(user.getId())
            .patientID(temp.getId())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
