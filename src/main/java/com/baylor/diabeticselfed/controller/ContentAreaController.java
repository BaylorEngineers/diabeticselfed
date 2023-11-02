package com.baylor.diabeticselfed.controller;
import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.service.ContentAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/content-areas")
public class ContentAreaController {

    @Autowired
    private ContentAreaService contentAreaService;

    @PostMapping
    public ResponseEntity<ContentArea> createContentArea(@RequestBody ContentArea contentArea) {
        ContentArea createdContentArea = contentAreaService.createContentArea(contentArea.getName());
        return new ResponseEntity<>(createdContentArea, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContentArea>> getAllContentAreas() {
        return new ResponseEntity<>(contentAreaService.getAllContentAreas(), HttpStatus.OK);
    }

}
