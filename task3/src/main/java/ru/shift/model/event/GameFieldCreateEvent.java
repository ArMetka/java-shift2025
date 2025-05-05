package ru.shift.model.event;

import ru.shift.common.enums.GameType;
import ru.shift.model.enums.ModelEventType;

public class GameFieldCreateEvent implements GameEvent<GameFieldCreateEvent> {
    private final GameType gameType;

    public GameFieldCreateEvent(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    @Override
    public ModelEventType getType() {
        return ModelEventType.FIELD_CREATE;
    }
}
