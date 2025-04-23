package ru.shift.highscore.event;

import ru.shift.common.dto.HighScore;

public class HighScoreEvent {
    private HighScore highScore;

    public HighScoreEvent(HighScore highScore) {
        this.highScore = highScore;
    }

    public HighScore getHighScore() {
        return highScore;
    }
}
