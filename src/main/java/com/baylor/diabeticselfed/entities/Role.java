package com.baylor.diabeticselfed.entities;

import com.baylor.diabeticselfed.user.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baylor.diabeticselfed.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  CLINICIAN_READ,
                  CLINICIAN_UPDATE,
                  CLINICIAN_DELETE,
                  CLINICIAN_CREATE
          )
  ),
  CLINICIAN(
          Set.of(
                  CLINICIAN_READ,
                  CLINICIAN_UPDATE,
                  CLINICIAN_DELETE,
                  CLINICIAN_CREATE
          )
  ),
  PATIENT(Collections.emptySet()),

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
