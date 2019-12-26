package com.optile.cs.error;

import org.springframework.http.HttpStatus;

public interface AppResponseErrorCode {
    String getMessage();

    HttpStatus getHttpStatus();
}