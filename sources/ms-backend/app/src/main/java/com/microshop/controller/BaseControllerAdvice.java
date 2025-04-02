package com.microshop.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.microshop.controller")
public class BaseControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(exception = DataIntegrityViolationException.class)
    public ResponseEntity<?> handle(HttpServletRequest request, Throwable throwable) {
        ProblemDetail detail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.CONFLICT, "Error while trying to save the entity.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
    }
}
