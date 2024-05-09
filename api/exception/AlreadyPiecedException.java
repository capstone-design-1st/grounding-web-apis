package com.grounding.api.exception;

public class AlreadyPiecedException extends RuntimeException {
    public AlreadyPiecedException(String message) {
        super(message);
    }
}
