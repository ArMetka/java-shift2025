package ru.shift.view.event;

import ru.shift.view.enums.ViewEventType;

public class RecordNameEvent implements ViewEvent<RecordNameEvent> {
    private final String name;

    public RecordNameEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public ViewEventType getType() {
        return ViewEventType.RECORD_NAME;
    }
}
