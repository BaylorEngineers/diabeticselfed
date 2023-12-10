package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import com.baylor.diabeticselfed.repository.SurveyRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private final UserRepository userRepository;

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

    public Optional<Survey> findSurveyByPatientAndQuestionAndDateT(Patient patient, Question question, Date dateT) {
        return surveyRepository.findByPatientAndQuestionAndDateT(patient, question, dateT);
    }

    public Boolean isFirstLoginToday(Patient patient, Date lastLoginDate) {

//        Date lastLoginDate = userRepository.getLastLoginDate(patient.getPatientUser().getId());
        
        Date currentDate = new Date();

        Calendar loginCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
        loginCalendar.setTime(lastLoginDate);
        currentCalendar.setTime(currentDate);


        return loginCalendar.get(Calendar.DAY_OF_MONTH) != currentCalendar.get(Calendar.DAY_OF_MONTH)
                || loginCalendar.get(Calendar.MONTH) != currentCalendar.get(Calendar.MONTH)
                || loginCalendar.get(Calendar.YEAR) != currentCalendar.get(Calendar.YEAR);


    }

}
