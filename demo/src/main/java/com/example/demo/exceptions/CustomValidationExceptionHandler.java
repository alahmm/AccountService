package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomValidationExceptionHandler {

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(jakarta.validation.ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .reduce("", (accumulator, message) -> accumulator + message + "; ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError.getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
