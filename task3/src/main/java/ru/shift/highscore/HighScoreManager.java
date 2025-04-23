package ru.shift.highscore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.dto.HighScore;
import ru.shift.common.enums.GameType;
import ru.shift.highscore.event.HighScoreEvent;
import ru.shift.highscore.listener.HighScoreListener;
import ru.shift.highscore.repository.HighScoreRepository;

import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
    private static final Logger log = LogManager.getLogger(HighScoreManager.class);

    private HighScoreRepository repository;
    private final List<HighScoreListener> listeners;
    private GameType gameType = GameType.NOVICE;
    private volatile int secondsElapsed;

    public HighScoreManager() {
        listeners = new ArrayList<>();
    }

    public void addHighScoreListener(HighScoreListener listener) {
        listeners.add(listener);
    }

    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void loadHighScores(String filepath) {
        repository = new HighScoreRepository(filepath);
        for (var type : GameType.values()) {
            notifyHighScoreListeners(new HighScoreEvent(repository.findByGameType(type)));
        }
    }

    public void checkForNewHighScore(String name) {
        if (repository.findByGameType(gameType).time() > secondsElapsed) {
            log.info("new high score: game type {}, name {}, time {} ", gameType.getName(), name, secondsElapsed);
            var score = new HighScore(gameType, name, secondsElapsed);
            repository.saveNewHighScore(score);
            notifyHighScoreListeners(new HighScoreEvent(score));
        }
    }

    private void notifyHighScoreListeners(HighScoreEvent e) {
        for (var listener : listeners) {
            listener.onNewHighScore(e);
        }
    }
}
