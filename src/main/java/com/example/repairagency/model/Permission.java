package com.example.repairagency.model;

public enum Permission {
    CUSTOMERS_READ("customers:read"),
    CUSTOMERS_WRITE("developers:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
