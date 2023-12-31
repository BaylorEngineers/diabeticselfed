package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.ModuleLog;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ModuleLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleLogService {

    private final ModuleLogRepository moduleLogRepository;

    public ModuleLog createModuleLog(Patient patient, Module module, ModuleProgress moduleProgress,
                                     LocalDateTime startT, LocalDateTime endT) {
        ModuleLog ml = new ModuleLog();
        ml.setPatient(patient);
        ml.setModule(module);
        ml.setModuleProgress(moduleProgress);
        ml.setStartT(startT);
        ml.setEndT(endT);

        return moduleLogRepository.save(ml);
    }

    public List<ModuleLog> getModuleLogByPatient(Patient patient) {
        return moduleLogRepository.findByPatient(patient);
    }

    public List<ModuleLog> getModuleLogByPatientAndModule(Patient patient, Module module) {
        return moduleLogRepository.findByPatientAndAndModule(patient, module);
    }

    public List<ModuleLog> findOverlap(Patient p, LocalDateTime startT, LocalDateTime endT) {
        return moduleLogRepository.findOverlap(p, startT, endT);
    }

}
