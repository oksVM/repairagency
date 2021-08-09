package com.example.repairagency.model;

import org.apache.el.stream.Stream;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.ADMIN)),
    MASTER(Set.of(Permission.MASTER)),
    CUSTOMER(Set.of(Permission.CUSTOMER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set <SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }


}
