package ru.shift.model.listener;

import ru.shift.model.event.GameStartEvent;

public interface GameStartEventListener extends GameEventListener<GameStartEvent> {
    void onStart(GameStartEvent e);
}
