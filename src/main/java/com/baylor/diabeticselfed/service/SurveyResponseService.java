package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.SurveyResponse;
import com.baylor.diabeticselfed.repository.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SurveyResponseService {
    private final SurveyResponseRepository surveyResponseRepository;

    public SurveyResponse recordResponse(Patient patient, Date dateT) {

        SurveyResponse sr = new SurveyResponse();
        sr.setDateT(dateT);
        sr.setPatient(patient);

        return surveyResponseRepository.save(sr);
    }

}
