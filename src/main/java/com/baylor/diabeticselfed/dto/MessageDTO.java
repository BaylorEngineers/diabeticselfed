package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Integer senderId;
    private String senderName;
    private Integer receiverId;
    private String receiverName;
    private String content;
    private Date time;
    private Boolean isRead;
}
