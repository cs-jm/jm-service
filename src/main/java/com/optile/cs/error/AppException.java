package com.optile.cs.error;

public class AppException extends RuntimeException {

    private AppErrorCode appErrorCode;

    public AppException(AppErrorCode appErrorCode) {
        super(appErrorCode.getMessage());

        this.appErrorCode = appErrorCode;
    }

    public AppErrorCode getAppErrorCode() {
        return appErrorCode;
    }
}
