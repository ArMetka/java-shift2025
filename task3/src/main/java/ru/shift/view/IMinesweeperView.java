package ru.shift.view;

import ru.shift.common.dto.HighScore;
import ru.shift.view.enums.ViewEventType;
import ru.shift.view.listener.ViewEventListener;

public interface IMinesweeperView {
    void addViewEventListener(ViewEventType type, ViewEventListener<?> listener);

    void setTimerValue(int newValue);

    void setNewHighScore(HighScore highScore);

    void setVisible(boolean visible);
}
