package com.techbank.cqrs_core.exception;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }

    public ConcurrencyException() {
        super("Concurrency Exception");
    }
}
