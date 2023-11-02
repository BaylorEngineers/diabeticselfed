package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ModuleProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/modules/progress")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ModuleProgressController {

    private final ModuleProgressService moduleProgressService;

    @Autowired
    private PatientRepository  patientRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping("/create/{patientId}/{moduleId}")
    public ResponseEntity<?> createModuleProgress(@PathVariable Integer patientId,
                                                  @PathVariable Integer moduleId) {
        try {

            Patient p = patientRepository.findById(patientId)
                    .orElseThrow();
            Module m = moduleRepository.findById(moduleId)
                    .orElseThrow();

            ModuleProgress mp = moduleProgressService.createModuleProgess(p, m, 0);

            return new ResponseEntity<>(mp, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{patientId}/{moduleId}/{percentage}")
    public ResponseEntity<?> updateModuleProgress(@PathVariable Integer patientId,
                                                  @PathVariable Integer moduleId,
                                                  @PathVariable Integer percentage) {

        try {

            Patient p = patientRepository.findById(patientId)
                    .orElseThrow();
            Module m = moduleRepository.findById(moduleId)
                    .orElseThrow();

            ModuleProgress mp = moduleProgressService.findByPatientAndModule(p, m)
                    .orElseThrow();

            ModuleProgress mp_new = moduleProgressService.updateModuleProgress(mp, percentage);

            return new ResponseEntity<>(mp_new, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
