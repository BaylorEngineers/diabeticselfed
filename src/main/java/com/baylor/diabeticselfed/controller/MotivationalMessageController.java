package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.SurveyDTO;
import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.MotivationalMessageService;
import com.baylor.diabeticselfed.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/motivationalmessage")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app/")
public class MotivationalMessageController {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MotivationalMessageService motivationalMessageService;


    @GetMapping("/get/{patientId}")
    public MotivationalMessage fetchSurveyByPatientId(@PathVariable Integer patientId, Principal connectedUser) {

        System.out.println("In the controller");

        Patient p = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        return motivationalMessageService.generateMotivationalMessage(p);
    }



}
