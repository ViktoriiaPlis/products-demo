package com.example.productdemo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    private final String vale;
    

}
