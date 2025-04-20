package ru.shift.exception;

public class InvalidThreadLimitException extends RuntimeException {
    public InvalidThreadLimitException(String message) {
        super(message);
    }
}
