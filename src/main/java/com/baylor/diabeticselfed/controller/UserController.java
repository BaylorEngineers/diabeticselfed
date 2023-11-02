package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.UserDTO;
import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.service.ClinicianService;
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
//    @GetMapping("/viewpatientsummary")

//    public ResponseEntity<List<ViewPatientSummary>> getAllPatientSummary() {
//        System.out.println("getAllPatientSummary: ");
//        List<ViewPatientSummary> patientSummary = clinicianService.getViewPatientSummary();
//        return ResponseEntity.ok(patientSummary);
//    }
    @GetMapping("/get-user-data")
    public UserDTO getUserData(@RequestParam Integer id) {
        UserDTO userData = service.getUserData(id);
        return userData;
    }
}
