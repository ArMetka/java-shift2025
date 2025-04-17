package ru.shift.view.event;

import ru.shift.view.enums.ViewEventType;

public class NewGameEvent implements ViewEvent<NewGameEvent> {
    @Override
    public ViewEventType getType() {
        return ViewEventType.NEW_GAME;
    }
}
