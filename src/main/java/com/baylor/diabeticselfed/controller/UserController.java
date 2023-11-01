package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.service.ClinicianService;
import com.baylor.diabeticselfed.user.ChangePasswordRequest;
import com.baylor.diabeticselfed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final ClinicianService clinicianService;

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
}
