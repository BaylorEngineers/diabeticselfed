package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class ForumPostDTO {
    Long patientId;
    String title;
    String content;
}
