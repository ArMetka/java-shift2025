package ru.shift.exception;

public class UnknownShapeException extends RuntimeException {
    public UnknownShapeException(String message) {
        super(message);
    }
}
