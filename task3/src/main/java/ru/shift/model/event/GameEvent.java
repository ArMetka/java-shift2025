package ru.shift.model.event;

import ru.shift.model.enums.ModelEventType;

public interface GameEvent<T extends GameEvent<T>> {
    ModelEventType getType();
}
