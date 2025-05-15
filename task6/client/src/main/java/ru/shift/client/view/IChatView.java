package ru.shift.client.view;

import ru.shift.client.view.enums.ViewEventType;
import ru.shift.client.view.listener.ViewEventListener;

public interface IChatView {
    void addViewEventListener(ViewEventType type, ViewEventListener listener);

    void setVisible(boolean visible);
}
