package ru.shift.model.event;

import ru.shift.model.enums.ModelEventType;

public class GameStartEvent implements GameEvent<GameStartEvent> {
    @Override
    public ModelEventType getType() {
        return ModelEventType.GAME_START;
    }
}
