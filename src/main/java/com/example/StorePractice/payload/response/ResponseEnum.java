package com.example.StorePractice.payload.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum ResponseEnum {
    DELETED("Item was deleted"),
    ADDED("The Item was successfully added."),
    UPDATED("The Item was successfully updated.");

    @Getter
    private final String resString;
}
