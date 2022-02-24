package com.github.redstylzz.backend.exception;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Log LOG = LogFactory.getLog(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException ex){
        LOG.error("Resource not found!", ex);

        ApiError apiError = new ApiError("Resource not found!", ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex){
        LOG.error("Given input is not processable!", ex);

        ApiError apiError = new ApiError("Given input is not processable!", ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){
        LOG.error("Invalid credentials", ex);

        ApiError apiError = new ApiError("Invalid credentials", ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleUnknownException(Throwable ex){
        LOG.error("Unknown Error!", ex);

        ApiError apiError = new ApiError("Unknown Error", ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
