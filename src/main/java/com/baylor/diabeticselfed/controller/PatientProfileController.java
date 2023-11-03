package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.MessageDTO;
import com.baylor.diabeticselfed.dto.PatientProfileDTO;
import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/patient-profile")
@RequiredArgsConstructor
public class PatientProfileController {

    @Autowired
    PatientService patientService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/submit")
    public ResponseEntity<?> updatePatient(@RequestBody PatientProfileDTO patientProfileDTO, Principal connectedUser) {
        try {

            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            var patient = patientService.findByUser(user)
                    .orElseThrow();

            if (Objects.equals(patient.getId(), patientProfileDTO.getPatientId())) {
                patientService.updatePatient(patientProfileDTO.getPatientId(), patientProfileDTO);

                return new ResponseEntity<>(patientProfileDTO, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("User do NOT have access", HttpStatus.I_AM_A_TEAPOT);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
