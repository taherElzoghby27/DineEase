package com.spring.boot.resturantbackend.utils.enums;

public enum RoleEnum {
    ADMIN("ADMIN"), USER("USER");
    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
