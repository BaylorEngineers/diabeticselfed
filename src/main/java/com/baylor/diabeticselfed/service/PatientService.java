package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.PatientProfileDTO;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public void updatePatient(Integer patientId, PatientProfileDTO patientProfileDTO) {
        Patient savePatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        //TODO: set changed values from patientProfileDTO

        Patient patient = new Patient();

        patient.setId(patientProfileDTO.getPatientId());
        patient.setName(patientProfileDTO.getName());
        patient.setDOB(patientProfileDTO.getDob());
        patient.setEmail(patientProfileDTO.getEmail());
        patient.setLevelOfEd(Patient.EducationLevel.valueOf(patientProfileDTO.getLevelOfEd()));
        patient.setType(Patient.Type.valueOf(patientProfileDTO.getType()));


        patientRepository.save(patient);
    }

    public Optional<Patient> findByUser(User user) {
        return patientRepository.findByPatientUser(user);
    }
}
