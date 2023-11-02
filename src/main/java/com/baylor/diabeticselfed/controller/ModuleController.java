package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.service.ModuleService;
import com.baylor.diabeticselfed.entities.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        Module createdModule = moduleService.createOrUpdateModule(module);
        return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        return new ResponseEntity<>(moduleService.getAllModules(), HttpStatus.OK);
    }

}
