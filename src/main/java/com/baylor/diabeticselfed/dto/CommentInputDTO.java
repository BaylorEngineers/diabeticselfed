package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class CommentInputDTO {
    private Long postId;
    private Long userId;
    private String content;

}