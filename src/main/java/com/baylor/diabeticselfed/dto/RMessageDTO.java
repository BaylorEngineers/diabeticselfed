package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class RMessageDTO {
    private Integer senderId;
    private Integer receiverId;
    private String content;
}
