package ru.shift.view.event;

import ru.shift.common.enums.GameType;
import ru.shift.view.enums.ViewEventType;

public class GameTypeEvent implements ViewEvent<GameTypeEvent> {
    private final GameType gameType;

    public GameTypeEvent(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    @Override
    public ViewEventType getType() {
        return ViewEventType.GAME_TYPE;
    }
}
