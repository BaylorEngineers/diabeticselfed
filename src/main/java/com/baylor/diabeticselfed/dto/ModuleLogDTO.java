package com.baylor.diabeticselfed.dto;

import lombok.Data;

@Data
public class ModuleLogDTO {

    private Integer patientId;
    private Integer moduleId;
    private String startT;
    private String endT;


}
