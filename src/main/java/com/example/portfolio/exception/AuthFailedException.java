package com.example.portfolio.exception;

public class AuthFailedException  extends RuntimeException {
    public AuthFailedException(String message) {
        super(message);
    }
}