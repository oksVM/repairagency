package com.example.repairagency.model;

public enum Permission {
    CUSTOMER("customer"),
    MASTER("master"),
    ADMIN("admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
