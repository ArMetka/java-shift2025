package ru.shift.client.view.listener;

import ru.shift.client.view.event.ConnectEvent;

public interface ConnectEventListener extends ViewEventListener {
    void onConnect(ConnectEvent event);
}
