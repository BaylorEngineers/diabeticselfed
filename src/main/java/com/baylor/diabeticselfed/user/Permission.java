package com.baylor.diabeticselfed.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    CLINICIAN_READ("clinician:read"),
    CLINICIAN_UPDATE("clinician:update"),
    CLINICIAN_CREATE("clinician:create"),
    CLINICIAN_DELETE("clinician:delete")

    ;

    @Getter
    private final String permission;
}
