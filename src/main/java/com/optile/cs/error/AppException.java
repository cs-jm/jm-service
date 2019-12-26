package com.optile.cs.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
    private AppErrorCode appErrorCode;
}
