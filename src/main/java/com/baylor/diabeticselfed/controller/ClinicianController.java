package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.service.ClinicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinicians")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ClinicianController {

    private final ClinicianService clinicianService;

    @GetMapping("/getAll")
    public List<Clinician> getAllClinicians(){
        List<Clinician> clinicians = clinicianService.getAllClinicians();
        return clinicians;
    }
}
