package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.ChatGPTRequest;
import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.repository.MotivationalMessageRepository;
import com.baylor.diabeticselfed.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationalMessageService {

    @Autowired
    private final MotivationalMessageRepository motivationalMessageRepository;
    private final SurveyRepository surveyRepository;
    private final ChatGPTService chatGPTService;

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
        try {
            List<Survey> surveys = surveyRepository.findTop3ByPatientOrderByDateTDesc(patient);

            surveys.forEach(survey -> System.out.println("Survey Date: " + survey.getDateT() + ", Answer: " + survey.getAnswer()));

            if (surveys.size() == 3 && surveys.stream().allMatch(survey -> !survey.getAnswer())) {
                String response = chatGPTService.executeCurlAndReturnResponseSync(prepareChatGPTRequest(patient));
                return recordMotivationalMessage(patient, new Date(), response);
            }

            return recordMotivationalMessage(patient, new Date(), "You're awesome");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return recordMotivationalMessage(patient, new Date(), "An error occurred. You're still awesome!");
        }
    }

    private int calculateAge(Date dateOfBirth) {
        LocalDate dob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }

    private ChatGPTRequest prepareChatGPTRequest(Patient patient) {
        int age = calculateAge(patient.getDOB());
        String prompt = "The patient " + patient.getName() + ", aged " + age +
                ", has not adhered to a healthy diet in the past few days. " +
                "Please provide a positive, motivational message in 30 words or less.";
        return new ChatGPTRequest(prompt);
    }

}
