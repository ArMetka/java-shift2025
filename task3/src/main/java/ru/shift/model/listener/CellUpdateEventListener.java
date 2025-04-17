package ru.shift.model.listener;

import ru.shift.model.event.CellUpdateEvent;

public interface CellUpdateEventListener extends GameEventListener<CellUpdateEvent> {
    void onCellUpdate(CellUpdateEvent e);
}
