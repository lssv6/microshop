package com.microshop.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.microshop.controller")
public class MainControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        List<FieldError> errors = methodArgumentNotValidException.getFieldErrors();

        Map<String, String> errorMap = errors.stream()
                .map(error -> Map.entry(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, _) -> x));
        problemDetail.setProperty("errors", errorMap);
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handle(DataIntegrityViolationException dataIntegrityViolationException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        return problemDetail;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handle(NoSuchElementException noSuchElementException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NO_CONTENT);
        return problemDetail;
    }
}
