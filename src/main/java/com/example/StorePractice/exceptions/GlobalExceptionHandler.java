package com.example.StorePractice.exceptions;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductsServiceException.class)
    public ResponseEntity<RuntimeErrorResponse> handleServiceException(ProductsServiceException ex, HttpServletRequest request) {
        RuntimeErrorResponse runtimeErrorResponse = new RuntimeErrorResponse(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeErrorResponse);
    }

    @ExceptionHandler(RateLimiterException.class)
    public ResponseEntity<RuntimeErrorResponse> handleRateLimiterException(RateLimiterException ex, HttpServletRequest request) {
        RuntimeErrorResponse runtimeErrorResponse = new RuntimeErrorResponse(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeErrorResponse);
    }


    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<RuntimeErrorResponse> handleRateLimiterException(RequestNotPermitted ex, HttpServletRequest request) {
        RuntimeErrorResponse runtimeErrorResponse = new RuntimeErrorResponse(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeErrorResponse);
    }

}
