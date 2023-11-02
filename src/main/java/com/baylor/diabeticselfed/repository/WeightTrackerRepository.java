package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Message;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.entities.WeightTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WeightTrackerRepository extends JpaRepository<WeightTracker, Long> {

    Optional<WeightTracker> findByPatientIdAndDateT(Integer patientId, Date dateT);

    List<WeightTracker> findByPatientId(Integer patientId);

}
