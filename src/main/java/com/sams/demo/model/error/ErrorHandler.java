package com.sams.demo.model.error;

import com.sams.demo.model.error.exception.SamsDemoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(SamsDemoException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ResponseEntity handleNotFoundException(SamsDemoException e) {

        return new ResponseEntity(NOT_FOUND);
    }
}