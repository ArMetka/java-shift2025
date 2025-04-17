package ru.shift;

import ru.shift.controller.MinesweeperController;
import ru.shift.highscore.HighScoreManager;
import ru.shift.model.IMinesweeperModel;
import ru.shift.model.MinesweeperModel;
import ru.shift.model.enums.ModelEventType;
import ru.shift.model.listener.GameEndEventListener;
import ru.shift.model.listener.GameStartEventListener;
import ru.shift.timer.GameTimer;
import ru.shift.timer.IGameTimer;
import ru.shift.view.IMinesweeperView;
import ru.shift.view.MinesweeperView;
import ru.shift.view.enums.ViewEventType;
import ru.shift.view.listener.GameTypeListener;
import ru.shift.view.listener.RecordNameListener;

public class Minesweeper {
    private static final String DEFAULT_HIGHSCORES_PATH = "highscores.data";

    public static void main(String[] args) {
        IGameTimer timer = new GameTimer();

        HighScoreManager highScoreManager = new HighScoreManager();
        timer.addTimerListener(e -> highScoreManager.setSecondsElapsed(e.getSecondsElapsed()));

        IMinesweeperModel model = new MinesweeperModel();
        model.addGameEventListener(
                ModelEventType.GAME_START,
                (GameStartEventListener) e -> timer.startTimer()
        );
        model.addGameEventListener(
                ModelEventType.GAME_END,
                (GameEndEventListener) e -> timer.stopTimer()
        );

        IMinesweeperView view = new MinesweeperView(model);
        view.addViewEventListener(
                ViewEventType.RECORD_NAME,
                (RecordNameListener) e -> highScoreManager.checkForNewHighScore(e.getName())
        );
        view.addViewEventListener(
                ViewEventType.GAME_TYPE,
                (GameTypeListener) e -> highScoreManager.setGameType(e.getGameType())
        );
        timer.addTimerListener(e -> view.setTimerValue(e.getSecondsElapsed()));
        highScoreManager.addHighScoreListener(e -> view.setNewHighScore(e.getHighScore()));
        highScoreManager.loadHighScores(DEFAULT_HIGHSCORES_PATH);

        new MinesweeperController(model, view);

        view.setVisible(true);
    }
}
