package com.optile.cs.error;

public class AppError {
    private String message;

    public AppError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}