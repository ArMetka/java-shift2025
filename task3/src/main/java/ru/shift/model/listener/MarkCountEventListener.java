package ru.shift.model.listener;

import ru.shift.model.event.MarkCountEvent;

public interface MarkCountEventListener extends GameEventListener<MarkCountEvent> {
    void onMarkCountUpdate(MarkCountEvent e);
}
