package com.mindease.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class APIResponse {
    private String message;
    private int status;
    private Object data;
    private LocalDateTime timestamp = LocalDateTime.now();

    public APIResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public APIResponse(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
