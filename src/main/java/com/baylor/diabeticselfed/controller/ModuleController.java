package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.service.ModuleService;
import com.baylor.diabeticselfed.entities.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody Module module) {

        try {
            if (moduleService.findModuleByName(module.getName()).isPresent()) {
                return new ResponseEntity<>("Module already exists", HttpStatus.FORBIDDEN)
            }
            else {
                Module createdModule = moduleService.createModule(module.getContentArea().getId(),
                        module.getName(),
                        module.getDescription(),
                        module.getFilePath());
                return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
            }

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Module>> getAllModules() {
        return new ResponseEntity<>(moduleService.getAllModules(), HttpStatus.OK);
    }

    @GetMapping("/get/{moduleName}")
    public ResponseEntity<?> getModuleByName(@PathVariable String moduleName) {
        try {
            Module m = moduleService.findModuleByName(moduleName)
                    .orElseThrow();
            return new ResponseEntity<>(m, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
