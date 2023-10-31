package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findById(Integer id);
    //Optional<Patient> findByEmail(String email);

}
