package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.PatientProfileDTO;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.MessageRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    private final MessageRepository messageRepository;

    public void updatePatient(Integer patientId, PatientProfileDTO patientProfileDTO) {
        Patient savePatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        savePatient.setName(patientProfileDTO.getName());
        savePatient.setDOB(patientProfileDTO.getDob());
        savePatient.setLevelOfEd(Patient.EducationLevel.valueOf(patientProfileDTO.getEducation()));
        savePatient.setType(Patient.Type.valueOf(patientProfileDTO.getType()));

        patientRepository.save(savePatient);
    }

    public Optional<Patient> findByUser(User user) {
        return patientRepository.findByPatientUser(user);
    }

    public Patient getPatientDetail(Integer patientId) {
        return patientRepository.findPatientWithUserDetails(patientId);
    }

    public List<Patient> getPatientsByClinician(Integer clinicianId) {
        // Get distinct sender IDs who have sent messages to the clinician
        List<Integer> senderIds = messageRepository.findDistinctSenderIdsByReceiverId(clinicianId);

        // Get patients based on the sender IDs
        List<Patient> patients = patientRepository.findByIdInCustom(senderIds);

        return patients;
    }
}
