package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RolesEnum {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String title;
}
