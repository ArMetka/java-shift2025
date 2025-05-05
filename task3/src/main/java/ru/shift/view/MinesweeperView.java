package ru.shift.view;

import ru.shift.common.dto.HighScore;
import ru.shift.common.enums.GameType;
import ru.shift.model.event.CellUpdateEvent;
import ru.shift.model.event.GameEndEvent;
import ru.shift.model.event.GameFieldCreateEvent;
import ru.shift.model.event.MarkCountEvent;
import ru.shift.view.enums.ViewEventType;
import ru.shift.view.event.CellEvent;
import ru.shift.view.event.GameTypeEvent;
import ru.shift.view.event.NewGameEvent;
import ru.shift.view.event.RecordNameEvent;
import ru.shift.view.listener.*;
import ru.shift.view.window.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MinesweeperView implements IMinesweeperView {
    private final EnumMap<ViewEventType, List<ViewEventListener<?>>> listeners;

    private final MainWindow mainWindow;
    private final SettingsWindow settingsWindow;
    private final HighScoresWindow highScoresWindow;
    private final RecordsWindow recordsWindow;
    private final WinWindow winWindow;
    private final LoseWindow loseWindow;

    public MinesweeperView() {
        listeners = new EnumMap<>(ViewEventType.class);
        for (var type : ViewEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }

        mainWindow = new MainWindow();
        settingsWindow = new SettingsWindow(mainWindow);
        highScoresWindow = new HighScoresWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);
        loseWindow = new LoseWindow(mainWindow);

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        winWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.setExitListener(e -> mainWindow.dispose());

        mainWindow.setCellListener(this::notifyCellListeners);
        settingsWindow.setGameTypeListener(this::notifyGameTypeListeners);
        recordsWindow.setNameListener(this::notifyRecordNameListeners);
        mainWindow.setNewGameMenuAction(e -> notifyNewGameListeners(new NewGameEvent()));
        winWindow.setNewGameListener(e -> notifyNewGameListeners(new NewGameEvent()));
        loseWindow.setNewGameListener(e -> notifyNewGameListeners(new NewGameEvent()));

        mainWindow.createGameField(GameType.NOVICE.getWidth(), GameType.NOVICE.getHeight());
        mainWindow.setBombsCount(GameType.NOVICE.getMinesCount());
    }

    @Override
    public void addViewEventListener(ViewEventType type, ViewEventListener<?> listener) {
        listeners.get(type).add(listener);
    }

    @Override
    public void setTimerValue(int newValue) {
        mainWindow.setTimerValue(newValue);
    }

    @Override
    public void setNewHighScore(HighScore highScore) {
        switch (highScore.gameType()) {
            case NOVICE -> highScoresWindow.setNoviceRecord(highScore.name(), highScore.time());
            case MEDIUM -> highScoresWindow.setMediumRecord(highScore.name(), highScore.time());
            case EXPERT -> highScoresWindow.setExpertRecord(highScore.name(), highScore.time());
        }
    }

    @Override
    public void handleCellUpdateEvent(CellUpdateEvent e) {
        var pos = e.getPos();
        mainWindow.setCellImage(pos.row(), pos.col(), e.getNewState().toGameImage());
    }

    @Override
    public void handleCreateFieldEvent(GameFieldCreateEvent e) {
        var type = e.getGameType();
        mainWindow.createGameField(type.getWidth(), type.getHeight());
        mainWindow.setBombsCount(type.getMinesCount());
    }

    @Override
    public void handleMarkCountEvent(MarkCountEvent e) {
        mainWindow.setBombsCount(e.getNewCount());
    }

    @Override
    public void handleGameEndEvent(GameEndEvent e) {
        switch (e.getGameEndType()) {
            case VICTORY -> {
                recordsWindow.setVisible(true);
                winWindow.setVisible(true);
            }
            case DEFEAT -> loseWindow.setVisible(true);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }

    private void notifyCellListeners(CellEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((CellEventListener) listener).onMouseClick(e);
        }
    }

    private void notifyGameTypeListeners(GameTypeEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((GameTypeListener) listener).onGameTypeChanged(e);
        }
    }

    private void notifyRecordNameListeners(RecordNameEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((RecordNameListener) listener).onRecordNameEntered(e);
        }
    }

    private void notifyNewGameListeners(NewGameEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((NewGameListener) listener).onNewGame(e);
        }
    }
}
