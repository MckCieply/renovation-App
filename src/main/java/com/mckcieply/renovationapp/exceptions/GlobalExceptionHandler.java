package com.mckcieply.renovationapp.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Date;

/**
 * Global exception handler for managing application-wide exceptions.
 * This class captures specific exceptions and provides a structured response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ConstraintViolationException.
     *
     * @param ex the exception thrown when validation constraints are violated
     * @return an ExceptionMessage containing error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage constraintViolationException(ConstraintViolationException ex) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getConstraintViolations().stream().map(error -> error.getMessage()).toList()
        );
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param ex the exception thrown when an illegal argument is provided
     * @return an ExceptionMessage containing the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage illegalArgumentException(IllegalArgumentException ex) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                Arrays.asList(ex.getMessage())
        );
    }
}
