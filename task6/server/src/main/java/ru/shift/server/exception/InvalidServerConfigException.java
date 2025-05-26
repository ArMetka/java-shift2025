package ru.shift.server.exception;

public class InvalidServerConfigException extends RuntimeException {
    public InvalidServerConfigException(String message) {
        super(message);
    }
}
