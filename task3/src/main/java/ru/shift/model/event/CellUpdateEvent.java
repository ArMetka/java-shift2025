package ru.shift.model.event;

import ru.shift.common.dto.CellPosition;
import ru.shift.common.enums.CellState;
import ru.shift.model.enums.ModelEventType;

public class CellUpdateEvent implements GameEvent<CellUpdateEvent> {
    private final CellPosition position;
    private final CellState newState;

    public CellUpdateEvent(CellPosition position, CellState newState) {
        this.position = position;
        this.newState = newState;
    }

    public CellPosition getPos() {
        return position;
    }

    public CellState getNewState() {
        return newState;
    }

    @Override
    public ModelEventType getType() {
        return ModelEventType.CELL_UPDATE;
    }
}
