package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.WeightTracker;
import com.baylor.diabeticselfed.repository.WeightTrackerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeightTrackerService {
    private final WeightTrackerRepository weightTrackerRepository;

    public WeightTracker addNewWeightTrackerReport(Integer patientId,
                                                   Date date,
                                                   Integer height,
                                                   Integer weight) {

        WeightTracker w = new WeightTracker();
        w.setPatientId(patientId);
        w.setDateT(date);
        w.setHeight(height);
        w.setWeight(weight);

        return weightTrackerRepository.save(w);
    }

    public Optional<WeightTracker> getReportByPatientIdANDDateT(Integer patientId, Date date) {
        return weightTrackerRepository.findByPatientIdAndDateT(patientId, date);
    }

}
