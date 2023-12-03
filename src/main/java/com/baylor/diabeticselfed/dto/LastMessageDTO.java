package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LastMessageDTO {
    private Integer receiverId;
    private String receiverFirstName;
    private String receiverLastName;
    private String content;  // Last message content
    private Date timestamp;  // Time of the last message
    private boolean isRead;
}
