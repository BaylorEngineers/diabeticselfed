package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public Survey submitSurvey(Patient patient, Date dateT, Question question, Boolean response) {
        Survey survey = new Survey();
        survey.setPatient(patient);
        survey.setDateT(dateT);
        survey.setQuestion(question);
        survey.setAnswer(response);

        return surveyRepository.save(survey);
    }

    public List<Survey> fetchSurveyByPatient(Patient patient) {
        return surveyRepository.findByPatient(patient);
    }


}
