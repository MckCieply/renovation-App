package com.mckcieply.renovationapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Represents a standardized error response for exceptions.
 */
@Data
@AllArgsConstructor
public class ExceptionMessage {

    /**
     * HTTP status code indicating the error type.
     */
    private int status;

    /**
     * Timestamp of when the error occurred.
     */
    private Date timestamp;

    /**
     * A user-friendly error message.
     */
    private String userMessage;

    /**
     * A developer-friendly error message or details.
     */
    private String developerMessage;

    /**
     * List of additional error details (optional).
     */
    private List<String> errors;
}
