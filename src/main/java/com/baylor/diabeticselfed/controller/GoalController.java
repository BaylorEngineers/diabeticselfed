package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.GoalDTO;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/setGoal")
    public ResponseEntity<?> setGoal(@RequestBody GoalDTO goalDTO) {
        try {
            Patient patient = patientRepository.findById(goalDTO.getPatientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            goalService.setGoal(patient, goalDTO.getWeightLossPercent());

            System.out.println("Goal Created!");

            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
