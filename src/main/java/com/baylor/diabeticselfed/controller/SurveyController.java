package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.SurveyDTO;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import com.baylor.diabeticselfed.service.SurveyResponseService;
import com.baylor.diabeticselfed.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app/")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyResponseService surveyResponseService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitSurvey(@RequestBody SurveyDTO surveyDTO, Principal connectedUser) {

        try {

            Optional<Patient> p = patientRepository.findById(surveyDTO.getPatientId());
//            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            Question q = questionRepository.findById(surveyDTO.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

            if (true || surveyService.findSurveyByPatientAndQuestionAndDateT(p.get(), q, formatter.parse(surveyDTO.getDateT())).isEmpty()) {
                surveyResponseService.recordResponse(p.get(), formatter.parse(surveyDTO.getDateT()));

                Survey s = surveyService.submitSurvey(p.get(), formatter.parse(surveyDTO.getDateT()), q, surveyDTO.getResponse());

                return new ResponseEntity<>(surveyDTO, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Already exists", HttpStatus.ALREADY_REPORTED);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fetch/{patientId}")
    public List<SurveyDTO> fetchSurveyByPatientId(@PathVariable Integer patientId, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Patient p = patientRepository.findByPatientUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        List<Survey> surveyList = surveyService.fetchSurveyByPatient(p);
        List<SurveyDTO> returnList = new ArrayList<>();
        for (Survey s : surveyList) {
            SurveyDTO temp = new SurveyDTO();
            temp.setDateT(s.getDateT().toString());
            temp.setPatientId(s.getPatient().getId());
            temp.setQuestionId(s.getQuestion().getId());
            temp.setResponse(s.getAnswer());
            returnList.add(temp);
        }
        return returnList;
    }

}
