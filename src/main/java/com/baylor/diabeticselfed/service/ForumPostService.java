package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.ResponseMessage;
import com.baylor.diabeticselfed.dto.newForumPostDTO;
import com.baylor.diabeticselfed.entities.Comment;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.CommentRepository;
import com.baylor.diabeticselfed.repository.ForumPostRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.user.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForumPostService {

    @Autowired
    private ForumPostRepository forumPostRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public ForumPost createPostForPatient(Long patientId, ForumPost forumPost) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        forumPost.setPatient(patient);
//        patient.getForumPosts().add(forumPost);

        return forumPostRepository.save(forumPost);
    }
    public Optional<ForumPost> findById(Long postId) {
        return forumPostRepository.findById(postId);
    }
    @Transactional
//    public List<ForumPost> findAllPostsByPatientId(Long patientId) {
//        // Check if patient exists
//        boolean patientExists = patientRepository.existsById(patientId);
//        if(!patientExists) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No patient found with ID: " + patientId);
//        }
//        return forumPostRepository.findByPatient_Id(patientId);
//    }
    public List<newForumPostDTO> findAllPostsByPatientId(Long patientId) {
        boolean patientExists = patientRepository.existsById(patientId);
        if(!patientExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No patient found with ID: " + patientId);
        }
        List<ForumPost> posts = forumPostRepository.findByPatient_Id(patientId);// fetch posts from the repository
        return posts.stream().map(DtoConverter::toForumPostDTO).collect(Collectors.toList());
    }
    public boolean postBelongsToPatient(Long postId, Long patientId) {
        return forumPostRepository.existsByIdAndPatient_Id(postId, patientId);
    }

    public List<ForumPost> findAll() {
        return forumPostRepository.findAll();
    }

    @Transactional
    public void deleteByIdAndPatientId(Long postId, Long patientId) {
        if(postBelongsToPatient(postId, patientId)) {
            forumPostRepository.deleteById(postId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post does not belong to the patient");
        }
    }

    public Comment createCommentForPost(Long postId, Long userId, String content) {
        ForumPost forumPost = forumPostRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Comment comment = new Comment();
        comment.setForumPost(forumPost);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCommentDate(new Date());

        return commentRepository.save(comment);
    }

    public ResponseMessage softDeleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (!optionalComment.isPresent()) {
            return new ResponseMessage(false, "Comment not found.");
        }

        Comment comment = optionalComment.get();

        if (comment.isDeleted()) {
            return new ResponseMessage(false, "Comment is already deleted.");
        }

        comment.setDeleted(true);
        comment.setContent("[This comment has been deleted]");
        commentRepository.save(comment);

        return new ResponseMessage(true, "Comment deleted successfully.");
    }
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
