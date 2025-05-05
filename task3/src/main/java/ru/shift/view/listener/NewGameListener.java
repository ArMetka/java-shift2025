package ru.shift.view.listener;

import ru.shift.view.event.NewGameEvent;

public interface NewGameListener extends ViewEventListener<NewGameEvent> {
    void onNewGame(NewGameEvent e);
}
