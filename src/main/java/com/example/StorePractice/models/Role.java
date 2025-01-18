package com.example.StorePractice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    @Getter
    private final String role;
}
