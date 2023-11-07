package com.baylor.diabeticselfed.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FirstWeightDTO {
    private String signUpToken;
    private Integer height;
    private Integer weight;
    private Date dateT;

}
