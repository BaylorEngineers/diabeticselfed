package com.baylor.diabeticselfed.controller;
import com.baylor.diabeticselfed.dto.ContentAreaDTO;
import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.service.ContentAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/content-areas")
@CrossOrigin(origins = "http://localhost:3000")
public class ContentAreaController {

    @Autowired
    private ContentAreaService contentAreaService;

    @PostMapping
    public ResponseEntity<ContentArea> createContentArea(@RequestBody ContentArea contentArea) {
        ContentArea createdContentArea = contentAreaService.createContentArea(contentArea.getName());
        return new ResponseEntity<>(createdContentArea, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<ContentArea>> getAllContentAreas() {
//        return new ResponseEntity<>(contentAreaService.getAllContentAreas(), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<ContentAreaDTO>> getAllContentAreas() {
        List<ContentAreaDTO> contentAreas = contentAreaService.getAllContentAreasDTO();
        return new ResponseEntity<>(contentAreas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentArea> getContentAreaById(@PathVariable Long id) {
        return contentAreaService.getContentAreaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
