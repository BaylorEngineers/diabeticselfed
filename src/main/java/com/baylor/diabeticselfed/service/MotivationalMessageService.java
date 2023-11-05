package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.MotivationalMessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationalMessageService {
    private final MotivationalMessageRepository motivationalMessageRepository;

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

        MotivationalMessage message = new MotivationalMessage();
        message.setMessage_content("Way to go!");
        return message;
    }


}
