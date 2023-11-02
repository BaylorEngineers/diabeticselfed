package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.repository.ContentAreaRepository;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ContentAreaRepository contentAreaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Module addModule(Integer contentAreaId, String name, String description, MultipartFile file) {
        String filePath = fileStorageService.storeFile(file);
        ContentArea contentArea = contentAreaRepository.findById(contentAreaId)
                .orElseThrow(() -> new EntityNotFoundException("Content Area not found"));

        Module module = new Module();
        module.setName(name);
        module.setDescription(description);
        module.setFilePath(filePath);
        module.setContentArea(contentArea);

        return moduleRepository.save(module);
    }

    public Optional<Module> findModuleByName(String name) {
        return moduleRepository.findByName(name);
    }

    public Module createModule(Integer contentAreaId, String name, String description, String filePath) {
        Module module = new Module();
        module.setName(name);
        module.setDescription(description);
        module.setFilePath(filePath);
        ContentArea contentArea = contentAreaRepository.findById(contentAreaId)
                .orElseThrow(() -> new EntityNotFoundException("Content Area not found"));
        module.setContentArea(contentArea);

        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

}
