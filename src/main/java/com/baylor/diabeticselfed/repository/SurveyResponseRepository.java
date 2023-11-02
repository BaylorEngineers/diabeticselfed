package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    @Query("SELECT sr FROM SurveyResponse sr WHERE sr.patient = ?1 ORDER BY sr.dateT DESC")
    List<SurveyResponse> findFirst7ByPatient(Patient patient);

}
