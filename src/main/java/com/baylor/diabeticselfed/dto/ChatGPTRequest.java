package com.baylor.diabeticselfed.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatGPTRequest {
    private String name;
    private int age;
    private String education;
    private String occupation;
    private String interests;
    private String background;
    private String goal;
}
