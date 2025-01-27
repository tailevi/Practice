package com.example.StorePractice.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class RateLimiterException extends RuntimeException{
    public RateLimiterException(String message) {
        super(message);
    }

    public RateLimiterException(String message, Throwable cause) {
        super(message, cause);
    }
}
