package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.service.ModuleService;
import com.baylor.diabeticselfed.entities.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
@RequiredArgsConstructor
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody Module module) {

        try {
            if (moduleService.findModuleByName(module.getName()).isPresent()) {
                return new ResponseEntity<>("Module already exists", HttpStatus.FORBIDDEN);
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getModuleById(@PathVariable Long id) {
        try {
            Module module = moduleService.findModuleById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));
            return new ResponseEntity<>(module, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Resource> getModulePDF(@PathVariable Long id) {
        try {
            Module module = moduleService.findModuleById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));

            String filePath = module.getFilePath();
            if (filePath == null || filePath.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File path is empty");
            }

            Resource file = new InputStreamResource(new FileInputStream(Paths.get(filePath).toFile()));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(file);

        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PDF file not found", e);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
