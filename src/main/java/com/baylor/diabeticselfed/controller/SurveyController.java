package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.SurveyDTO;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import com.baylor.diabeticselfed.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitSurvey(@RequestBody SurveyDTO surveyDTO) {

        try {
            Patient p = patientRepository.findById(surveyDTO.getPatientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            Question q = questionRepository.findById(surveyDTO.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            Survey s = surveyService.submitSurvey(p, formatter.parse(surveyDTO.getDateT()), q, surveyDTO.getResponse());

            return new ResponseEntity<>(s, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fetch/{patientId}")
    public List<Survey> fetchSurveyByPatientId(@PathVariable Integer patientId) {

        Patient p = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        return surveyService.fetchSurveyByPatient(p);
    }

}
