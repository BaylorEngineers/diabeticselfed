package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.service.ClinicianService;
import com.baylor.diabeticselfed.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clinicians")
@RequiredArgsConstructor
public class ClinicianController {

    private final ClinicianService clinicianService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/viewpatientsummary")

//    public ResponseEntity<List<ViewPatientSummary>> getViewPatientSummary(@RequestParam("patientId") int patientId) {
//        List<ViewPatientSummary> patientSummary = clinicianService.getAllPatientSummary(patientId);
//        return ResponseEntity.ok(patientSummary);
//    }
    public ResponseEntity<List<ViewPatientSummary>> getAllPatientSummary() {
        List<ViewPatientSummary> patientSummary = clinicianService.getAllPatientSummary();
        return ResponseEntity.ok(patientSummary);
    }

}
