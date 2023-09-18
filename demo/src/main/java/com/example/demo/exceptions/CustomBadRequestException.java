package com.example.demo.exceptions;

import java.time.LocalDateTime;

public class CustomBadRequestException extends RuntimeException {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String path;
    private final String message;

    public CustomBadRequestException(String message, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = 400;
        this.error = "Bad Request";
        this.message = message; // Custom message
        this.path = path;       // Custom path
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
