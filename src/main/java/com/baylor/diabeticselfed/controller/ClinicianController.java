package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.ClinicianDTO;
import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.service.ClinicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clinicians")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ClinicianController {

    private final ClinicianService clinicianService;

    @GetMapping("/getAll")
    public List<ClinicianDTO> getAllClinicians(){
        List<Clinician> clinicians = clinicianService.getAllClinicians();
        List<ClinicianDTO> dtoList = new ArrayList<>();
        for (Clinician clinician: clinicians
             ) {
            ClinicianDTO cli = new ClinicianDTO();
            cli.setClinicianId(clinician.getId());
            cli.setUserId(clinician.getClinicianUser().getId());
            cli.setName(clinician.getName());
            cli.setEmail(clinician.getEmail());
            dtoList.add(cli);
        }
        return dtoList;
    }
}

