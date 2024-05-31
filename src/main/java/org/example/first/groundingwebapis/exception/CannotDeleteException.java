package org.example.first.groundingwebapis.exception;

public class CannotDeleteException extends RuntimeException {
    public CannotDeleteException(String message) {
        super(message);
    }
}