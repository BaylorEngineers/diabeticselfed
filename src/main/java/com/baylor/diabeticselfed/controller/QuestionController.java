package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.QuestionDTO;
import com.baylor.diabeticselfed.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.uploadQuestion(questionDTO.getDescription());
        return new ResponseEntity<>("Question Uploaded", HttpStatus.OK);
    }

}
