package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findByPatient(Patient patient);

    List<Survey> findByOrderByIdAsc();

}
