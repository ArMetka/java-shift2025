package ru.shift.common.exception;

public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable e) {
        super(message, e);
    }
}
