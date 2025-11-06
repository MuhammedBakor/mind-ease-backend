package com.mindease.requests;

public record LoginRequest(
        String email,
        String password
) {
}
