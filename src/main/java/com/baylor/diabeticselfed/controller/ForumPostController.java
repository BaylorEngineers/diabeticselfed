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
import com.baylor.diabeticselfed.user.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public newForumPostDTO toDTO(ForumPost post) {
        newForumPostDTO dto = new newForumPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPostDate(post.getPostDate());
        dto.setOwnerFirstName(post.getPatient().getFirstname());
        dto.setOwnerLastName(post.getPatient().getLastname());
        dto.setComments(post.getComments().stream().map(this::toDTO).collect(Collectors.toList()));
        return dto;
    }

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
    public ResponseEntity<newForumPostDTO> getPostById(@PathVariable Long postId) {
        try {
            ForumPost post = forumPostService.findById(postId).orElse(null);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }
            newForumPostDTO dto = DtoConverter.toForumPostDTO(post);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{postId}/patient/{patientId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @PathVariable Long patientId) {
        try {
            // Check if the post exists
            if (!forumPostRepository.existsById(postId)) {
                System.out.println("Post with ID " + postId + " does not exist.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
            }

            // Check if the post belongs to the patient
            if (forumPostService.postBelongsToPatient(postId, patientId)) {
                forumPostService.deleteByIdAndPatientId(postId, patientId);
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

    @GetMapping("/posts/{patientId}")
    public ResponseEntity<Object> getAllPostsByPatientId(@PathVariable Long patientId) {
        try {
            List<newForumPostDTO> dtos = forumPostService.findAllPostsByPatientId(patientId);
            return ResponseEntity.ok(dtos);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/allposts")
    public ResponseEntity<List<newForumPostDTO>> getAllPosts() {
        try {
            List<ForumPost> posts = forumPostService.findAll();
            List<newForumPostDTO> dtos = posts.stream()
                    .map(DtoConverter::toForumPostDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentInputDTO input) {
        Comment comment = forumPostService.createCommentForPost(input.getPostId(), input.getUserId(), input.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("comments/{id}/user/{userID}")
    public ResponseEntity<String> softDeleteComment(@PathVariable Long id, @PathVariable Long userID) {
        try {
            Comment comment = commentRepository.findCommentById(id);

            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Comment not found!");
            }

            if (!comment.getUser().getId().equals(userID.intValue())) {

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
    public ResponseEntity<Comment> commentOnComment(@PathVariable Long commentId, @RequestBody CommentInputDTO childCommentDTO) {
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent comment not found"));

        Comment childComment = new Comment();
        // Assuming you have a userRepository and forumPostRepository to fetch User and ForumPost
        User user = userRepository.findById(childCommentDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        childComment.setUser(user);
        childComment.setContent(childCommentDTO.getContent());
        childComment.setCommentDate(new Date());
        childComment.setParentComment(parentComment);

        Comment savedChildComment = forumPostService.saveComment(childComment);
        return ResponseEntity.ok(savedChildComment);
    }

}
