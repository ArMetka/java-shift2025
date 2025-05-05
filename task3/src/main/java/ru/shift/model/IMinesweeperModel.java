package ru.shift.model;

import ru.shift.common.dto.CellPosition;
import ru.shift.common.enums.GameType;
import ru.shift.model.enums.ModelEventType;
import ru.shift.model.listener.GameEventListener;

public interface IMinesweeperModel {
    void changeGameType(GameType gameType);

    void createNewGame();

    void addGameEventListener(ModelEventType type, GameEventListener<?> listener);

    void openCell(CellPosition pos);

    void markCell(CellPosition pos);

    void easyOpenCell(CellPosition pos);
}
