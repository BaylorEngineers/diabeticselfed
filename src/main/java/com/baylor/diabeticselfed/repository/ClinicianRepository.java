package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClinicianRepository extends JpaRepository<Clinician, Long> {

    Optional<Clinician> findByEmail(String email);
    Optional<Clinician> findByClinicianUser(User user);
    Optional<Clinician> findById(Long id);
    @Query("SELECT v FROM ViewPatientSummary v")
    List<ViewPatientSummary> findAllPatientSummary();


}
