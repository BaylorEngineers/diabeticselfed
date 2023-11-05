package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findByPatient(Patient patient);

    Optional<Survey> findByPatientAndQuestionAndDateT(Patient patient, Question question, Date dateT);

}
