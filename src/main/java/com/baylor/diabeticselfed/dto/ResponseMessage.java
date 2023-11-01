package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class ResponseMessage {
    private boolean success;
    private String message;

    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // getters and setters ...
}