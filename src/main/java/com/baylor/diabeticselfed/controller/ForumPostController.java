package com.baylor.diabeticselfed.controller;


import com.baylor.diabeticselfed.dto.ForumPostDTO;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/forum-posts")
public class ForumPostController {

    @Autowired
    private ForumPostService forumPostService;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping(value = "/post", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ForumPost> postForum(@RequestBody ForumPostDTO post) {
        System.out.println("Inside postForum method. Received post: " + post);
        var newpost = new ForumPost();
        newpost.setPatient(patientRepository.findById(post.getPatientId()).get());
        newpost.setContent(post.getContent());
        newpost.setTitle(post.getTitle());
        newpost.setPostDate(new Date());
        ForumPost savedPost = forumPostService.createPostForPatient(post.getPatientId(), newpost);
        return ResponseEntity.ok(savedPost);
    }
}
