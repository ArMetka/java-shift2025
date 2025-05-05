package ru.shift.view.listener;

import ru.shift.view.event.GameTypeEvent;

public interface GameTypeListener extends ViewEventListener<GameTypeEvent> {
    void onGameTypeChanged(GameTypeEvent e);
}
