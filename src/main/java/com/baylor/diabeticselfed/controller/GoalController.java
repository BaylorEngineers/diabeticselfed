package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.GoalDTO;
import com.baylor.diabeticselfed.entities.Goal;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/setGoal")
    public ResponseEntity<?> setGoal(@RequestBody GoalDTO goalDTO, Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Patient patient = patientRepository.findByPatientUser(user)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            if (goalService.findGoalByPatient(patient).isEmpty()) {
                goalService.setGoal(patient, goalDTO.getWeightLossPercent());

                System.out.println("Goal Created!");

                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            else {
                Goal goal = goalService.retrieveGoalByPatient(patient);

                goal.setWeightLossPercent(goalDTO.getWeightLossPercent());

                goalService.updateGoal(goal);

                return new ResponseEntity<>(goal, HttpStatus.OK);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/updateGoal")
    public ResponseEntity<?> updateGoal(@RequestBody GoalDTO goalDTO, Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Patient patient = patientRepository.findByPatientUser(user)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            if (goalService.findGoalByPatient(patient).isPresent()) {
                Goal goal = goalService.retrieveGoalByPatient(patient);

                goal.setWeightLossPercent(goalDTO.getWeightLossPercent());

                goalService.updateGoal(goal);

                return new ResponseEntity<>(goal, HttpStatus.OK);

            }
            else {
                return new ResponseEntity<>("User goal does NOT exist", HttpStatus.FORBIDDEN);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getGoal")
    public ResponseEntity<?> getGoalByPatientId(Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Patient patient = patientRepository.findByPatientUser(user)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            if (goalService.findGoalByPatient(patient).isPresent()) {
                Goal g = goalService.findGoalByPatient(patient)
                        .orElseThrow();

                return new ResponseEntity<>(g, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("There is no goal yet", HttpStatus.I_AM_A_TEAPOT);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
