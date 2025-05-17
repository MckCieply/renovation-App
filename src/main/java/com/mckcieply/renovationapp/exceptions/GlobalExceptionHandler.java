package com.mckcieply.renovationapp.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;

/**
 * Global exception handler for managing application-wide exceptions.
 * This class captures specific exceptions and provides a structured response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ConstraintViolationException.
     *
     * @return an ExceptionMessage containing error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleConstraintViolationException(ConstraintViolationException ex) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Validation failed for the provided input.",
                "Constraint violations occurred: " + ex.getMessage(),
                ex.getConstraintViolations().stream()
                        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                        .toList()
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
    public ExceptionMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Invalid argument provided.",
                ex.getMessage(),
                null
        );
    }

    /**
     * Handles DataIntegrityViolationException.
     *
     * @param ex the exception thrown when a database constraint is violated
     * @return an ExceptionMessage containing error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionMessage handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorType = null;
        String rootCauseMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException hibEx) {
            if ("23503".equals(hibEx.getSQLState())) {      // PostgreSQL error code 23503 indicates foreign key violation
                errorType = "FOREIGN_KEY_VIOLATION";
            }
        }

        return new ExceptionMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                "Unable to process the request due to database constraints.",
                rootCauseMessage,
                errorType != null ? List.of(errorType) : null
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage handleAuthenticationException(AuthenticationException ex) {
        return new ExceptionMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                "Authentication error.",
                ex.getMessage(),
                List.of("AUTHENTICATION_ERROR")
        );
    }

    /**
     * Handles generic Exception.
     *
     * @param ex the exception thrown for unexpected errors
     * @return an ExceptionMessage containing error details
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleGenericException(Exception ex) {
        return new ExceptionMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                "An unexpected error occurred.",
                ex.getMessage(),
                null
        );
    }
}