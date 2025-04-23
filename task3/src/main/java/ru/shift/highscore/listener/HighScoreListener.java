package ru.shift.highscore.listener;

import ru.shift.highscore.event.HighScoreEvent;

public interface HighScoreListener {
    void onNewHighScore(HighScoreEvent e);
}
