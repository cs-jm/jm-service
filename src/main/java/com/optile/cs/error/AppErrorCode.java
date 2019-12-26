package com.optile.cs.error;

import org.springframework.http.HttpStatus;

public interface AppErrorCode {
    String getMessage();
    HttpStatus getHttpStatus();
}