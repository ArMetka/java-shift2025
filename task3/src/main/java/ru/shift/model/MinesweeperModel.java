package ru.shift.model;

import ru.shift.common.dto.CellPosition;
import ru.shift.common.enums.CellState;
import ru.shift.common.enums.GameType;
import ru.shift.model.enums.GameEndType;
import ru.shift.model.enums.ModelEventType;
import ru.shift.model.event.*;
import ru.shift.model.listener.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MinesweeperModel implements IMinesweeperModel {
    private final EnumMap<ModelEventType, List<GameEventListener<?>>> listeners;
    private GameField gameField;
    private GameType gameType = GameType.NOVICE;
    private boolean isStarted;

    public MinesweeperModel() {
        listeners = new EnumMap<>(ModelEventType.class);
        for (var type : ModelEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
        createNewGame();
    }

    @Override
    public void changeGameType(GameType gameType) {
        this.gameType = gameType;
        createNewGame();
    }

    @Override
    public void createNewGame() {
        if (isStarted) {
            notifyGameEndListeners(new GameEndEvent(GameEndType.CANCEL));
        }
        isStarted = false;

        gameField = new GameField(gameType.getWidth(), gameType.getHeight(), gameType.getMinesCount());
        notifyGameFieldCreateListeners(new GameFieldCreateEvent(gameType));
    }

    @Override
    public void addGameEventListener(ModelEventType type, GameEventListener<?> listener) {
        listeners.get(type).add(listener);
    }

    @Override
    public void openCell(CellPosition pos) {
        if (!isStarted) {
            gameField.generateField(pos);
            isStarted = true;
            notifyGameStartListeners(new GameStartEvent());
        }

        Cell cell = gameField.getCell(pos);

        if (cell.isMarked() || cell.isOpen()) {
            return;
        }

        if (cell.isMine()) {
            defeat();
            return;
        }

        openChained(cell);

        checkForVictory();
    }

    @Override
    public void markCell(CellPosition pos) {
        Cell cell = gameField.getCell(pos);

        if (cell.isOpen()) {
            return;
        }

        if (gameField.toggleCellMark(pos)) {
            notifyCellUpdateListeners(new CellUpdateEvent(
                    pos,
                    cell.isMarked() ? CellState.MARKED : CellState.CLOSED
            ));
            notifyMarkCountListeners(new MarkCountEvent(gameType.getMinesCount() - gameField.getMarkCount()));
        }

        checkForVictory();
    }

    @Override
    public void easyOpenCell(CellPosition pos) {
        Cell cell = gameField.getCell(pos);

        if (!cell.isOpen() || cell.isMarked() || cell.getNearbyMinesCount() == 0) {
            return;
        }

        if (gameField.getNearbyMarksCount(pos) != cell.getNearbyMinesCount()) {
            return;
        }

        for (var cellToOpen : gameField.getCellsToEasyOpen(pos)) {
            if (cellToOpen.isMine()) {
                defeat();
                return;
            }

            if (!cellToOpen.isOpen()) { // check to avoid double-open caused by openChained method
                openChained(cellToOpen);
            }
        }

        checkForVictory();
    }

    private void openChained(Cell cell) {
        if (cell.getNearbyMinesCount() == 0) {
            for (var cellToOpen : gameField.getCellsToChainOpen(cell.getPos())) {
                if (cellToOpen.isMarked()) {
                    continue;
                }

                gameField.setCellOpen(cellToOpen);
                notifyCellUpdateListeners(new CellUpdateEvent(
                        cellToOpen.getPos(),
                        CellState.OPEN.setMinesNearby(cellToOpen.getNearbyMinesCount())
                ));
            }
        } else if (!cell.isMarked()) {
            gameField.setCellOpen(cell);
            notifyCellUpdateListeners(new CellUpdateEvent(
                    cell.getPos(),
                    CellState.OPEN.setMinesNearby(cell.getNearbyMinesCount())
            ));
        }
        notifyMarkCountListeners(new MarkCountEvent(gameType.getMinesCount() - gameField.getMarkCount()));
    }

    private void defeat() {
        revealMines();
        notifyGameEndListeners(new GameEndEvent(GameEndType.DEFEAT));
    }

    private void checkForVictory() {
        if (!gameField.checkAllCellsOpen()) {
            return;
        }

        revealMines();
        notifyGameEndListeners(new GameEndEvent(GameEndType.VICTORY));
    }

    private void revealMines() {
        for (var mine : gameField.getMines()) {
            notifyCellUpdateListeners(new CellUpdateEvent(mine.getPos(), CellState.MINE));
        }
    }

    private void notifyGameFieldCreateListeners(GameFieldCreateEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((GameFieldCreateEventListener) listener).onFieldCreate(e);
        }
    }

    private void notifyCellUpdateListeners(CellUpdateEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((CellUpdateEventListener) listener).onCellUpdate(e);
        }
    }

    private void notifyGameStartListeners(GameStartEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((GameStartEventListener) listener).onStart(e);
        }
    }

    private void notifyGameEndListeners(GameEndEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((GameEndEventListener) listener).onGameEnd(e);
        }
    }

    private void notifyMarkCountListeners(MarkCountEvent e) {
        for (var listener : listeners.get(e.getType())) {
            ((MarkCountEventListener) listener).onMarkCountUpdate(e);
        }
    }
}
