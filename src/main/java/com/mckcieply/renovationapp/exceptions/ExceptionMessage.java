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
     * List of error messages or details.
     */
    private List<String> errors;

}
