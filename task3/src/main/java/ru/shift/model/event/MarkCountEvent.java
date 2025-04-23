package ru.shift.model.event;

import ru.shift.model.enums.ModelEventType;

public class MarkCountEvent implements GameEvent<MarkCountEvent> {
    private final int newCount;

    public MarkCountEvent(int newCount) {
        this.newCount = newCount;
    }

    public int getNewCount() {
        return newCount;
    }

    @Override
    public ModelEventType getType() {
        return ModelEventType.MARK_COUNT;
    }
}
