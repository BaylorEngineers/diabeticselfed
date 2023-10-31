package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.ForumPostRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ForumPostService {

    @Autowired
    private ForumPostRepository forumPostRepository;
    @Autowired
    private PatientRepository patientRepository;

    public ForumPost createPostForPatient(Long patientId, ForumPost forumPost) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        forumPost.setPatient(patient);
        patient.getForumPosts().add(forumPost);

        return forumPostRepository.save(forumPost);
    }

}
