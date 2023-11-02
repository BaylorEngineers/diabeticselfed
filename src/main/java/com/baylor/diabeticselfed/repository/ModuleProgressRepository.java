package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Integer> {

    List<ModuleProgress> findByPatient(Patient patient);
}
