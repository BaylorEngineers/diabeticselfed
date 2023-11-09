package com.baylor.diabeticselfed.controller;


import com.baylor.diabeticselfed.dto.CommentDTO;
import com.baylor.diabeticselfed.dto.CommentInputDTO;
import com.baylor.diabeticselfed.dto.ForumPostDTO;
import com.baylor.diabeticselfed.dto.newForumPostDTO;
import com.baylor.diabeticselfed.entities.Comment;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.repository.CommentRepository;
import com.baylor.diabeticselfed.repository.ForumPostRepository;
import com.baylor.diabeticselfed.repository.PatientRepository;
import com.baylor.diabeticselfed.repository.UserRepository;
import com.baylor.diabeticselfed.service.ForumPostService;
import com.baylor.diabeticselfed.model.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/forum-posts")
public class ForumPostController {

    @Autowired
    private ForumPostService forumPostService;
    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    public CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCommentDate(comment.getCommentDate());
        dto.setCommenterFirstName(comment.getUser().getFirstname());
        dto.setCommenterLastName(comment.getUser().getLastname());
        dto.setChildComments(comment.getChildComments().stream().map(this::toDTO).collect(Collectors.toList()));
        return dto;
    }
    @GetMapping("/{postId}")
    public ResponseEntity<newForumPostDTO> getPostById(@PathVariable Long postId, Principal connectedUser) {
        try {
            ForumPost post = forumPostService.findById(postId).orElse(null);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }
            newForumPostDTO dto = DtoConverter.toForumPostDTO(post, connectedUser);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{postId}/patient/{patientId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @PathVariable Long patientId, Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            var patient = patientRepository.findByPatientUser(user).get();
            // Check if the post exists
            if (!forumPostRepository.existsById(postId)) {
                System.out.println("Post with ID " + postId + " does not exist.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
            }

            // Check if the post belongs to the patient
            if (forumPostService.postBelongsToPatient(postId, patient.getId().longValue())) {
                forumPostService.deleteByIdAndPatientId(postId, patient.getId().longValue());
                System.out.println("Deleted post with ID: " + postId);
                return ResponseEntity.noContent().build();
            } else {
                System.out.println("Post with ID " + postId + " does not belong to patient with ID " + patientId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Post does not belong to the patient");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // Log the exception for diagnosis
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/post", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ForumPost> postForum(@RequestBody ForumPostDTO post, Principal connectedUser) {
        System.out.println("Inside postForum method. Received post: " + post);
        var newpost = new ForumPost();
        var patient = patientRepository.findByPatientUser((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).get();
        newpost.setPatient(patient);
        newpost.setContent(post.getContent());
        newpost.setTitle(post.getTitle());
        newpost.setPostDate(new Date());
        ForumPost savedPost = forumPostService.createPostForPatient(patient.getId().longValue(), newpost);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/posts/{patientId}")
    public ResponseEntity<Object> getAllPostsByPatientId(@PathVariable Long patientId, Principal connectedUser) {
        try {
            var patient = patientRepository.findByPatientUser((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).get();
            List<newForumPostDTO> dtos = forumPostService.findAllPostsByPatientId(patient.getId().longValue());
            return ResponseEntity.ok(dtos);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/allposts")
    public ResponseEntity<List<newForumPostDTO>> getAllPosts(Principal principal) {
        try {
            List<ForumPost> posts = forumPostService.findAll();
            List<newForumPostDTO> dtos = posts.stream()
                    .map(post -> DtoConverter.toForumPostDTO(post, principal))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentInputDTO input, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Comment comment = forumPostService.createCommentForPost(input.getPostId(), user.getId().longValue(), input.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("comments/{id}/user/{userID}")
    public ResponseEntity<String> softDeleteComment(@PathVariable Long id, @PathVariable Long userID, Principal connectedUser) {
        try {
            Comment comment = commentRepository.findCommentById(id);
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Comment not found!");
            }

            if (!comment.getUser().getId().equals(user.getId())) {

                System.out.println("Input UserID:"+userID);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User ID does not match the comment's owner.");
            }

            forumPostService.softDeleteComment(id);
            return ResponseEntity.ok("Comment successfully soft deleted.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/comments/{commentId}")
    public ResponseEntity<Comment> commentOnComment(@PathVariable Long commentId, @RequestBody CommentInputDTO childCommentDTO, Principal connectedUser) {
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent comment not found"));
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Comment childComment = new Comment();
        // Assuming you have a userRepository and forumPostRepository to fetch User and ForumPost
         user = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        childComment.setUser(user);
        childComment.setContent(childCommentDTO.getContent());
        childComment.setCommentDate(new Date());
        childComment.setParentComment(parentComment);

        Comment savedChildComment = forumPostService.saveComment(childComment);
        return ResponseEntity.ok(savedChildComment);
    }

}
