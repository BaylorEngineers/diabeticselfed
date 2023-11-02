package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ModuleProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleProgressService {
    private final ModuleProgressRepository moduleProgressRepository;

    public ModuleProgress createModuleProgess(Patient patient, Module module, Integer percentage) {
        ModuleProgress mp = new ModuleProgress();

        mp.setPatient(patient);
        mp.setModule(module);
        mp.setCompleted_percentage(percentage);

        return moduleProgressRepository.save(mp);
    }

    public ModuleProgress updateModuleProgress(ModuleProgress moduleProgress, Integer percentage) {
        moduleProgress.setCompleted_percentage(percentage);
        return moduleProgressRepository.save(moduleProgress);
    }

    public Optional<ModuleProgress> findByPatientAndModule(Patient patient, Module module) {
        return moduleProgressRepository.findByPatientAndAndModule(patient, module);
    }

}
