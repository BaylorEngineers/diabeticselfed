package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.ModuleLogDTO;
import com.baylor.diabeticselfed.entities.ModuleLog;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ModuleProgressRepository;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ModuleLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api/modules/log")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ModuleLogController {

    private final ModuleLogService moduleLogService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModuleProgressRepository moduleProgressRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createModuleLog(@RequestBody ModuleLogDTO moduleLogDTO) {

        try {

            Patient p = patientRepository.findById(moduleLogDTO.getPatientId())
                    .orElseThrow();
            Module m = moduleRepository.findById(moduleLogDTO.getModuleId())
                    .orElseThrow();
            ModuleProgress mp = moduleProgressRepository.findByPatientAndAndModule(p, m)
                    .orElseThrow();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime start = LocalDateTime.parse(moduleLogDTO.getStartT(), formatter);
            LocalDateTime end = LocalDateTime.parse(moduleLogDTO.getEndT(), formatter);

            ModuleLog ml = moduleLogService.createModuleLog(p, m, mp, start, end);

            return new ResponseEntity<>(ml, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
