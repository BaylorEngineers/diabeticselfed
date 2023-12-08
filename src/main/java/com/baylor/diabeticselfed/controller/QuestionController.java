package com.baylor.diabeticselfed.controller;

import com.baylor.diabeticselfed.dto.QuestionDTO;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.uploadQuestion(questionDTO.getDescription());
        return new ResponseEntity<>("Question Uploaded", HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://stingray-app-uf6iy.ondigitalocean.app")
    @GetMapping("/get/{questionId}")
    public ResponseEntity<Optional<Question>> getPatientDetail(@PathVariable Integer questionId) {
        try {
            Optional<Question> question = questionService.getQuestion(questionId);
            return ResponseEntity.ok(question);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
