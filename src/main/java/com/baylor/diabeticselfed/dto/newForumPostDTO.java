package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class newForumPostDTO {
    private Long id;
    private String title;
    private String content;
    private Date postDate;
    private String ownerFirstName;
    private String ownerLastName;
    private List<CommentDTO> comments;
}
