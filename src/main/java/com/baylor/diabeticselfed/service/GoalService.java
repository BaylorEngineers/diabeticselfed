package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Goal;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;

    public Goal setGoal(Patient patientUser, Integer weightLossPercent) {
        Goal g = new Goal();
        g.setPatient(patientUser);
        g.setWeightLossPercent(weightLossPercent);
        g.setAccomplished(false);

        return goalRepository.save(g);
    }

    public Optional<Goal> findGoalByPatient(Patient patient) {
        return goalRepository.findByPatient(patient);
    }

    public Goal retrieveGoalByPatient(Patient patient) {
        return goalRepository.findByPatient(patient)
                .orElseThrow();
    }


    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

}
