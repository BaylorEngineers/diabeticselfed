package com.baylor.diabeticselfed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRequest {
    private String model;
    private String prompt;
    private int max_tokens;
}
