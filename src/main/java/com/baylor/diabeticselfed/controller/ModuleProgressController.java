package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ModuleProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

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
                                                  @PathVariable Integer moduleId,
                                                  Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            Patient p = patientRepository.findByPatientUser(user)
                    .orElseThrow();
            Module m = moduleRepository.findById(moduleId)
                    .orElseThrow();

            if (moduleProgressService.findByPatientAndModule(p, m).isEmpty()) {
                ModuleProgress mp = moduleProgressService.createModuleProgess(p, m, 0);

                return new ResponseEntity<>(mp, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("This module progress entry already exists", HttpStatus.FORBIDDEN);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{patientId}/{moduleId}/{percentage}")
    public ResponseEntity<?> updateModuleProgress(@PathVariable Integer patientId,
                                                  @PathVariable Integer moduleId,
                                                  @PathVariable Integer percentage,
                                                  Principal connectedUser) {

        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            Patient p = patientRepository.findByPatientUser(user)
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

    @GetMapping("/get/{patientId}")
    public List<ModuleProgress> getModuleProgressByPatientId(@PathVariable Integer patientId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Patient p = patientRepository.findByPatientUser(user)
                .orElseThrow();
        return moduleProgressService.findByPatient(p);
    }

}
