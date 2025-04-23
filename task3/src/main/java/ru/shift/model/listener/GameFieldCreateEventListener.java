package ru.shift.model.listener;

import ru.shift.model.event.GameFieldCreateEvent;

public interface GameFieldCreateEventListener extends GameEventListener<GameFieldCreateEvent> {
    void onFieldCreate(GameFieldCreateEvent e);
}
