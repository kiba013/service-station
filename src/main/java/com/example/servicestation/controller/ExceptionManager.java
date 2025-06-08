package com.example.servicestation.controller;

import com.example.servicestation.errors.AccessDeniedException;
import com.example.servicestation.errors.InvalidStatusTransitionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<String> handleInvalidTransaction(InvalidStatusTransitionException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleInvalidTransaction(AccessDeniedException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
}
