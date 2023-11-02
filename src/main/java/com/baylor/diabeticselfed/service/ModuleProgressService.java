package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ModuleProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleProgressService {
    private final ModuleProgressRepository moduleProgressRepository;

    public ModuleProgress createModuleProgess(Patient patient, Module module, Integer percentage) {
        ModuleProgress mp = new ModuleProgress();



        return moduleProgressRepository.save(mp);
    }
}
