package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Date commentDate;
    private String commenterFirstName;
    private String commenterLastName;
    private Integer userId;
    private List<CommentDTO> childComments; // for nested comments
}
