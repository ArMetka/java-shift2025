package ru.shift.view.event;

import ru.shift.view.enums.ViewEventType;

public interface ViewEvent<T extends ViewEvent<T>> {
    ViewEventType getType();
}
