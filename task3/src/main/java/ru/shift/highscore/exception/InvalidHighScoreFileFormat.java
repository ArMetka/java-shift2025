package ru.shift.highscore.exception;

public class InvalidHighScoreFileFormat extends RuntimeException {
    public InvalidHighScoreFileFormat(String message) {
        super(message);
    }
}
