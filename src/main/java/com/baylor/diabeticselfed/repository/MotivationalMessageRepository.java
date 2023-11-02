package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MotivationalMessageRepository extends JpaRepository<MotivationalMessage, Long> {

    @Query("SELECT mm FROM MotivationalMessage mm WHERE mm.patient=?1 ORDER BY mm.dateT DESC")
    List<MotivationalMessage> findByPatient(Patient patient);

}
