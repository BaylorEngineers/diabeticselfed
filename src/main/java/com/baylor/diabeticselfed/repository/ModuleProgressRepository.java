package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import com.baylor.diabeticselfed.entities.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Integer> {

    List<ModuleProgress> findByPatient(Patient patient);

    Optional<ModuleProgress> findByPatientAndAndModule(Patient patient, Module module);
}
