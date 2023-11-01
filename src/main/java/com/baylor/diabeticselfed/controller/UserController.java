package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.UserDTO;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.user.ChangePasswordRequest;
import com.baylor.diabeticselfed.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final ClinicianService clinicianService;

    @RequestMapping("/change-password")
    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/viewpatientsummary")

    public ResponseEntity<List<ViewPatientSummary>> getAllPatientSummary() {
        List<ViewPatientSummary> patientSummary = clinicianService.getViewPatientSummary();
        return ResponseEntity.ok(patientSummary);
    }
    @GetMapping("/get-user-data")
    public ResponseEntity<UserDTO> getUserData(@RequestParam String email) {
        try {
            UserDTO userData = service.getUserData(email);

            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/clinicians")
    public ResponseEntity<List<User>> getAllClinicians() {
        try {
            List<User> clinicians = service.getAllClinicians();
            return ResponseEntity.ok(clinicians);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

//    @GetMapping("/clinicians-with-patients")
//    public ResponseEntity<Map<User, List<User>>> getCliniciansWithPatients() {
//        try {
//            Map<User, List<User>> cliniciansWithPatients = service.getCliniciansWithPatients();
//            return ResponseEntity.ok(cliniciansWithPatients);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
