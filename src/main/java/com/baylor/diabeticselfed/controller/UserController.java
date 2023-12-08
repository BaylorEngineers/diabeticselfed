package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.ClinicianNoteDTO;
import com.baylor.diabeticselfed.dto.UserDTO;
import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.entities.ClinicianNote;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.service.ClinicianService;
import com.baylor.diabeticselfed.user.ChangePasswordRequest;
import com.baylor.diabeticselfed.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final ClinicianService clinicianService;

    private final JdbcTemplate jdbcTemplate;

    @RequestMapping("/change-password")
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-user-data")
    public UserDTO getUserData(@RequestParam Integer id) {
        UserDTO userData = service.getUserData(id);
        return userData;
    }

    //view patient summary

    @GetMapping("/viewpatientsummary")
    public ResponseEntity<List<ViewPatientSummary>> getAllPatientSummary() {
        try {
            System.out.println("getAllPatientSummary: ");
            List<ViewPatientSummary> patientSummary = clinicianService.getViewPatientSummary();
            return ResponseEntity.ok(patientSummary);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/notes/{patientId}")
    public ResponseEntity<List<ClinicianNote>> getNotesByPatientId(@PathVariable Integer patientId) {
        List<ClinicianNote> notes = clinicianService.getNotesByPatientId(patientId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/notes")
    public ResponseEntity<ClinicianNote> addNote(@RequestBody ClinicianNoteDTO clinicianNoteDTO) {
        try {
            ClinicianNote clinicianNote = clinicianService.addNote(clinicianNoteDTO.getPatientId(), clinicianNoteDTO.getNote());
            return ResponseEntity.ok(clinicianNote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
