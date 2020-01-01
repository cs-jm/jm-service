package com.optile.cs.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppResponseException extends RuntimeException {
    private final AppResponseErrorCode appResponseErrorCode;
}
