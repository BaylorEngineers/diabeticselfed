package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPatientUser(User user);
    Optional<Patient> findById(Long id);
    boolean existsById(Long id);

    @Query("SELECT p FROM Patient p JOIN FETCH p.patientUser u WHERE p.id = :patientId")
    Patient findPatientWithUserDetails(@Param("patientId") Integer patientId);

}
