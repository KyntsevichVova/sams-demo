package com.sams.demo.model.error.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Data
public class SamsDemoException extends RuntimeException {

    private final String message;
    private final HttpStatus status;
    private final String [] errorMessageParams;

    private SamsDemoException(String message, HttpStatus status, String... errorMessageParams) {
        super(message);
        this.message = message;
        this.status = status;
        this.errorMessageParams = errorMessageParams;
    }

    public static SamsDemoException badRequestException(String message, String... errorMessageParams) {

        return new SamsDemoException(message, BAD_REQUEST, errorMessageParams);
    }

    public static SamsDemoException internalServerException(String message) {

        return new SamsDemoException(message, INTERNAL_SERVER_ERROR);
    }

    public static SamsDemoException entityNotFoundException(String message, String... errorMessageParams) {

        return new SamsDemoException(message, NOT_FOUND, errorMessageParams);
    }
}