package ru.shift.model.listener;

import ru.shift.model.event.GameEndEvent;

public interface GameEndEventListener extends GameEventListener<GameEndEvent> {
    void onGameEnd(GameEndEvent e);
}
