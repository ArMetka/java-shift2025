package ru.shift.server.exception;

public class UnsupportedMessageTypeException extends RuntimeException {
    public UnsupportedMessageTypeException(String message) {
        super(message);
    }
}
