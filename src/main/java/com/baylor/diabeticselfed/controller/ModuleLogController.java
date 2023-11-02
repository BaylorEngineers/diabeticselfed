package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.entities.ModuleLog;
import com.baylor.diabeticselfed.entities.ModuleProgress;
import com.baylor.diabeticselfed.repository.ModuleProgressRepository;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ModuleLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    
}
