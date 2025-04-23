package ru.shift.model.event;

import ru.shift.model.enums.GameEndType;
import ru.shift.model.enums.ModelEventType;

public class GameEndEvent implements GameEvent<GameEndEvent> {
    private final GameEndType gameEndType;

    public GameEndEvent(GameEndType gameEndType) {
        this.gameEndType = gameEndType;
    }

    public GameEndType getGameEndType() {
        return gameEndType;
    }

    @Override
    public ModelEventType getType() {
        return ModelEventType.GAME_END;
    }
}
