package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleLog;
import com.baylor.diabeticselfed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleLogRepository extends JpaRepository<ModuleLog, Integer> {

    List<ModuleLog> findByPatientAndAndModule(Patient patient, Module module);
}
