package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Optional<Goal> findByPatientId(Integer patientId);

}
