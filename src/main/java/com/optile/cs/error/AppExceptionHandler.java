package com.optile.cs.error;

import com.optile.cs.job.JobController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice(assignableTypes = {JobController.class})
public class AppExceptionHandler {
    private final static Logger LOGGER = Logger.getLogger(AppExceptionHandler.class.getName());

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<AppError> handleRunTimeException(RuntimeException exception) {
        LOGGER.severe(exception.getMessage());

        return new ResponseEntity<>(new AppError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<AppError> handleAppException(AppException exception) {
        LOGGER.severe(exception.getMessage());

        return new ResponseEntity<>(new AppError(exception.getMessage()), exception.getAppErrorCode().getHttpStatus());
    }
}
