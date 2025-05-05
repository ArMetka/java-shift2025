package ru.shift.model.exception;

public class InvalidGameFieldParametersException extends RuntimeException {
    public InvalidGameFieldParametersException(String message) {
        super(message);
    }
}
