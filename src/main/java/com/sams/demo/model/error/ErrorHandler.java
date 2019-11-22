package com.sams.demo.model.error;

import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ResponseEntity handle(MethodArgumentNotValidException ex, Locale locale) {

        List<ErrorMessage> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> formatErrorMessage(error, locale))
                .collect(toList());

        return ResponseBuilder
                .failure()
                .withErrorMessage(errors)
                .withHttpStatus(BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(SamsDemoException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ResponseEntity handleNotFoundException(SamsDemoException e) {

        return new ResponseEntity(NOT_FOUND);
    }

    private ErrorMessage formatErrorMessage(ObjectError error, Locale locale) {

        ErrorMessage errorMessage = new ErrorMessage();

        if (error instanceof FieldError) {

            FieldError fe = (FieldError) error;

            String message = messageSource.getMessage(
                    fe.getDefaultMessage(),
                    new String [] {fe.getField()},
                    locale);

            errorMessage.setField(fe.getField());
            errorMessage.setMessage(message);
        }

        return errorMessage;
    }
}