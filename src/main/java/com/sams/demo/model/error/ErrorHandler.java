package com.sams.demo.model.error;

import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

import static com.sams.demo.model.error.ErrorCode.FIELD_INVALID_LENGTH;
import static com.sams.demo.model.error.ErrorCode.UNEXPECTED_ERROR;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handle(HttpMessageNotReadableException ex, Locale locale) {

        if (ex.getRootCause() instanceof SamsDemoException) {
            return handle((SamsDemoException)ex.getRootCause(), locale);
        } else {
            return handleUnknownReasonException(ex, locale);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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
    public ResponseEntity handle(SamsDemoException ex, Locale locale) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(messageSource.getMessage(
                ex.getMessage(),
                ex.getErrorMessageParams(),
                locale));

        return ResponseBuilder
                .failure()
                .withErrorMessage(singletonList(errorMessage))
                .withHttpStatus(ex.getStatus())
                .build();
    }

    private ResponseEntity handleUnknownReasonException(Exception ex, Locale locale) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(messageSource.getMessage(
                UNEXPECTED_ERROR,
                new String [] {ex.getMessage()},
                locale));

        return ResponseBuilder
                .failure()
                .withErrorMessage(singletonList(errorMessage))
                .withHttpStatus(INTERNAL_SERVER_ERROR)
                .build();
    }

    private ErrorMessage formatErrorMessage(ObjectError error, Locale locale) {

        ErrorMessage errorMessage = new ErrorMessage();

        if (error instanceof FieldError) {

            FieldError fe = (FieldError) error;

            String message = messageSource.getMessage(
                    fe.getDefaultMessage(),
                    FIELD_INVALID_LENGTH.equalsIgnoreCase(fe.getDefaultMessage())
                    ? new String [] {
                            requireNonNull(fe.getArguments())[2].toString(),
                            requireNonNull(fe.getArguments())[1].toString()}
                    : new String [] {fe.getField()},
                    locale);

            errorMessage.setField(fe.getField());
            errorMessage.setMessage(message);
        }

        return errorMessage;
    }
}