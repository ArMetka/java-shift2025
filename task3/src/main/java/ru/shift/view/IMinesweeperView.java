package ru.shift.view;

import ru.shift.common.dto.HighScore;
import ru.shift.model.event.CellUpdateEvent;
import ru.shift.model.event.GameEndEvent;
import ru.shift.model.event.GameFieldCreateEvent;
import ru.shift.model.event.MarkCountEvent;
import ru.shift.view.enums.ViewEventType;
import ru.shift.view.listener.ViewEventListener;

public interface IMinesweeperView {
    void addViewEventListener(ViewEventType type, ViewEventListener<?> listener);

    void setTimerValue(int newValue);

    void setNewHighScore(HighScore highScore);

    void handleCellUpdateEvent(CellUpdateEvent e);

    void handleCreateFieldEvent(GameFieldCreateEvent e);

    void handleMarkCountEvent(MarkCountEvent e);

    void handleGameEndEvent(GameEndEvent e);

    void setVisible(boolean visible);
}
