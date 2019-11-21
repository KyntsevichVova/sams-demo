package com.sams.demo.model.error.exception;

import lombok.Data;

@Data
public class SamsDemoException extends RuntimeException {

    private final String message;

    public SamsDemoException(String message) {
        super(message);
        this.message = message;
    }
}