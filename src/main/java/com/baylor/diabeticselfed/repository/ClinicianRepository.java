package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.model.ViewPatientSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicianRepository extends JpaRepository<ViewPatientSummary, Integer> {

    @Query("SELECT v FROM ViewPatientSummary v")
    List<ViewPatientSummary> findAllPatientSummary();
}
