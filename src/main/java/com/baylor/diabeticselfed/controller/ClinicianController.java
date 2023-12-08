package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.ClinicianDTO;
import com.baylor.diabeticselfed.dto.PatientDTO;
import com.baylor.diabeticselfed.dto.PatientProfileDTO;
import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ClinicianService;
import com.baylor.diabeticselfed.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clinicians")
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
@RequiredArgsConstructor
public class ClinicianController {

    private final ClinicianService clinicianService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;

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
            cli.setEmail(clinician.getClinicianUser().getEmail());
            dtoList.add(cli);
        }
        return dtoList;
    }
    @GetMapping("/getAllPatient")
    public List<ClinicianDTO> getAllPatient(){
        List<Patient> clinicians = patientRepository.findAll();
        List<ClinicianDTO> dtoList = new ArrayList<>();
        for (Patient clinician: clinicians
        ) {
            ClinicianDTO cli = new ClinicianDTO();
            cli.setClinicianId(clinician.getId());
            cli.setUserId(clinician.getPatientUser().getId());
            cli.setName(clinician.getName());
            cli.setEmail(clinician.getEmail());
            dtoList.add(cli);
        }
        return dtoList;
    }

    @GetMapping("/{clinicianId}/patients")
    public ResponseEntity<List<PatientDTO>> getPatientsByClinician(@PathVariable Integer clinicianId) {
        List<Patient> patients = patientService.getPatientsByClinician(clinicianId);

        // Convert patients to DTOs if needed
        List<PatientDTO> patientDTOs = patients.stream()
                .map(patient -> {
                    PatientDTO patientDTO = new PatientDTO();
                    // Set patientDTO properties
                    patientDTO.setUserId(patient.getPatientUser().getId());
                    patientDTO.setPatientId(patient.getId());
                    patientDTO.setName(patient.getName());
                    patientDTO.setEmail(patient.getPatientUser().getEmail());
                    return patientDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(patientDTOs, HttpStatus.OK);
    }
}

