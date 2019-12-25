package com.optile.cs.error;

import org.springframework.http.HttpStatus;

public enum AppErrorCode {
    ERROR_EMPTY_JOB_FILE("Failed to submit job : Empty job file", HttpStatus.BAD_REQUEST),
    ERROR_SAVE_JOB_FILE("Failed to submit job : Error saving file", HttpStatus.BAD_REQUEST),
    ERROR_SCHEDULE_JOB("Failed to submit job : Error scheduling job", HttpStatus.BAD_REQUEST),;

    private String message;
    private HttpStatus httpStatus;

    AppErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}