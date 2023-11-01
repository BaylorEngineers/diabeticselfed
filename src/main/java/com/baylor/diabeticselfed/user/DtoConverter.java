package com.baylor.diabeticselfed.user;

import com.baylor.diabeticselfed.dto.CommentDTO;
import com.baylor.diabeticselfed.dto.newForumPostDTO;
import com.baylor.diabeticselfed.entities.Comment;
import com.baylor.diabeticselfed.entities.ForumPost;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static newForumPostDTO toForumPostDTO(ForumPost post) {
        newForumPostDTO dto = new newForumPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPostDate(post.getPostDate());
        dto.setOwnerFirstName(post.getPatient().getPatientUser().getFirstname());
        dto.setOwnerLastName(post.getPatient().getPatientUser().getLastname());

        // Convert comments and set
        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(DtoConverter::toCommentDTO)
                .collect(Collectors.toList());
        dto.setComments(commentDTOs);

        return dto;
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCommentDate(comment.getCommentDate());
        dto.setCommenterFirstName(comment.getUser().getFirstname());
        dto.setCommenterLastName(comment.getUser().getLastname());

        // Convert child comments and set
        List<CommentDTO> childCommentDTOs = comment.getChildComments().stream()
                .map(DtoConverter::toCommentDTO)
                .collect(Collectors.toList());
        dto.setChildComments(childCommentDTOs);

        return dto;
    }
}
