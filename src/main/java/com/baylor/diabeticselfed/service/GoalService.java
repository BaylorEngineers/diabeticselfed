package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Goal;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;

    public Goal setGoal(Patient patientUser, Integer weightLossPercent) {
        Goal g = new Goal();
        g.setPatientId(patientUser.getId());
        g.setWeightLossPercent(weightLossPercent);
        g.setAccomplished(false);

        return goalRepository.save(g);
    }

}
