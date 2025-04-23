package ru.shift.view.listener;

import ru.shift.view.event.RecordNameEvent;

public interface RecordNameListener extends ViewEventListener<RecordNameEvent> {
    void onRecordNameEntered(RecordNameEvent e);
}
