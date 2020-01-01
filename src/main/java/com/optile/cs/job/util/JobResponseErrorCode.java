package com.optile.cs.job.util;

import com.optile.cs.error.AppResponseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JobResponseErrorCode implements AppResponseErrorCode {
    RESPONSE_ERROR_001("Failed to submit job : Empty job file", HttpStatus.BAD_REQUEST),
    RESPONSE_ERROR_002("Failed to submit job : Error saving file", HttpStatus.BAD_REQUEST),
    RESPONSE_ERROR_003("Failed to submit job : Error scheduling job", HttpStatus.BAD_REQUEST),
    ;

    private String message;
    private HttpStatus httpStatus;
}
