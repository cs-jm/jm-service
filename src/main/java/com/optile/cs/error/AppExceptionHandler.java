package com.optile.cs.error;

import com.optile.cs.job.JobController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice(assignableTypes = {JobController.class})
public class AppExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class.getName());

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<AppError> handleRunTimeException(RuntimeException exception) {
        LOGGER.error(exception.getMessage());

        return new ResponseEntity<>(new AppError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<AppError> handleAppException(AppException exception) {
        LOGGER.error(exception.getMessage());

        return new ResponseEntity<>(new AppError(exception.getAppErrorCode().getMessage()), exception.getAppErrorCode().getHttpStatus());
    }
}
