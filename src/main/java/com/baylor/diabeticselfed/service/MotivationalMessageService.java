package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.repository.MotivationalMessageRepository;
import com.baylor.diabeticselfed.repository.SurveyRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationalMessageService {
    private final MotivationalMessageRepository motivationalMessageRepository;

    @Autowired
    private final SurveyRepository surveyRepository;

    public MotivationalMessage recordMotivationalMessage(Patient patient, Date dateT, String content) {
        MotivationalMessage m = new MotivationalMessage();
        m.setPatient(patient);
        m.setDateT(dateT);
        m.setMessage_content(content);

        return motivationalMessageRepository.save(m);
    }

    public List<MotivationalMessage> fetchMotivationalMessage(Patient patient) {
        return motivationalMessageRepository.findByPatient(patient);
    }

    public MotivationalMessage generateMotivationalMessage(Patient patient) {

        //check if answered no three times
        List<Survey> surveyList = surveyRepository.findByOrderByIdAsc();

        List<Survey> lastThree = surveyList.subList(Math.max(surveyList.size() - 3, 0), surveyList.size());

        boolean getMotivationalMessage = true;
        MotivationalMessage message = new MotivationalMessage();

        System.out.println(lastThree.size());


        for (Survey response: lastThree) {
            if (response.getAnswer()) {
                getMotivationalMessage = false;
            }
        }

        if(getMotivationalMessage && lastThree.size() == 3) {
            message.setPatient(patient);
            message.setMessage_content("Way to go!");
        } else {
            message.setPatient(patient);
            message.setMessage_content(null);
        }
        return message;


    }


}
