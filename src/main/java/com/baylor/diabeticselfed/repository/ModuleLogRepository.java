package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleLog;
import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ModuleLogRepository extends JpaRepository<ModuleLog, Integer> {

    List<ModuleLog> findByPatientAndAndModule(Patient patient, Module module);

    List<ModuleLog> findByPatient(Patient patient);

    @Query("SELECT ml FROM ModuleLog ml " +
            "WHERE (ml.startT < :endT AND ml.endT > :startT) " +
            "AND (ml.patient = :patient) " +
            "ORDER BY ml.startT")
    List<ModuleLog> findOverlap(@Param("patient") Patient patient,
                                @Param("startT") LocalDateTime startT,
                                @Param("endT") LocalDateTime endT);

}
