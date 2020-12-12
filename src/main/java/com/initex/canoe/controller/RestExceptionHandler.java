package com.initex.canoe.controller;

import com.initex.canoe.dto.ExceptionResponse;
import com.initex.canoe.exception.CanoeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.initex.canoe.services.utils.Constants.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CanoeException.class)
    protected ResponseEntity<Object> handleValidationException(CanoeException exception) {
        switch (exception.getMessage()) {
            case DATABASE_READ_ERROR:
            case DATABASE_SAVE_ERROR:
            case DATABASE_UPDATE_ERROR:
                return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
