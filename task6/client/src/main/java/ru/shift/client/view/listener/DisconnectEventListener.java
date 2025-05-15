package ru.shift.client.view.listener;

import ru.shift.client.view.event.DisconnectEvent;

public interface DisconnectEventListener extends ViewEventListener {
    void onDisconnect(DisconnectEvent event);
}
