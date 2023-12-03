package com.baylor.diabeticselfed.model;

import com.baylor.diabeticselfed.dto.CommentDTO;
import com.baylor.diabeticselfed.dto.newForumPostDTO;
import com.baylor.diabeticselfed.entities.Comment;
import com.baylor.diabeticselfed.entities.ForumPost;
import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static newForumPostDTO toForumPostDTO(ForumPost post, Principal connectedUser) {
        newForumPostDTO dto = new newForumPostDTO();
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPostDate(post.getPostDate());
        if(post.getPatient().getPatientUser().getId() == user.getId() || post.getPatient().getPatientUser().getRole() == Role.CLINICIAN || user.getRole() == Role.CLINICIAN){
            dto.setOwnerFirstName(post.getPatient().getPatientUser().getFirstname());
            dto.setOwnerLastName(post.getPatient().getPatientUser().getLastname());
        }else {

            dto.setOwnerFirstName("Anonymous");
            dto.setOwnerLastName("");
        }

        // Convert comments and set
        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(comment -> DtoConverter.toCommentDTO(comment, connectedUser))
                .collect(Collectors.toList());
        dto.setComments(commentDTOs);

        return dto;
    }

    public static CommentDTO toCommentDTO(Comment comment, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCommentDate(comment.getCommentDate());
        if(comment.getUser().getId() == user.getId() || comment.getUser().getRole() == Role.CLINICIAN || user.getRole() == Role.CLINICIAN) {
            dto.setCommenterFirstName(comment.getUser().getFirstname());
            dto.setCommenterLastName(comment.getUser().getLastname());
        }else{
            dto.setCommenterFirstName("Anonymous");
            dto.setCommenterLastName("");
        }
        dto.setUserId(comment.getUser().getId());

        // Convert child comments and set
        List<CommentDTO> childCommentDTOs = comment.getChildComments().stream()
                .map(childComment -> DtoConverter.toCommentDTO(childComment, connectedUser))
                .collect(Collectors.toList());
        dto.setChildComments(childCommentDTOs);

        return dto;
    }
}
