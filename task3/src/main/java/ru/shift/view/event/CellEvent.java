package ru.shift.view.event;

import ru.shift.view.enums.ButtonType;
import ru.shift.view.enums.ViewEventType;

public class CellEvent implements ViewEvent<CellEvent> {
    private final int x;
    private final int y;
    private final ButtonType buttonType;

    public CellEvent(int x, int y, ButtonType buttonType) {
        this.x = x;
        this.y = y;
        this.buttonType = buttonType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    @Override
    public ViewEventType getType() {
        return ViewEventType.CELL;
    }
}
