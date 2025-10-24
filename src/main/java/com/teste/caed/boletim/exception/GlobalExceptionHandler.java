package com.teste.caed.boletim.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ApiError body = new ApiError();
        body.path = req.getRequestURI();
        body.status = HttpStatus.BAD_REQUEST.value();
        body.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        body.message = "Validation error";
        body.fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ApiError.FieldError(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        ApiError body = new ApiError();
        body.path = req.getRequestURI();
        body.status = HttpStatus.BAD_REQUEST.value();
        body.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        body.message = ex.getMessage();
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiError body = new ApiError();
        body.path = req.getRequestURI();
        body.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        body.error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        body.message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}