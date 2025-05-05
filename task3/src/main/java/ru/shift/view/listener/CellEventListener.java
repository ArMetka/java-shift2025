package ru.shift.view.listener;

import ru.shift.view.event.CellEvent;

public interface CellEventListener extends ViewEventListener<CellEvent> {
    void onMouseClick(CellEvent e);
}
