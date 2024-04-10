package com.mckcieply.renovationapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor

public class ExceptionMessage {
    private int status;
    private Date timestamp;
    private List<String> errors;

}
