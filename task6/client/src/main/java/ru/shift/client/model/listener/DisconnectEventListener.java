package ru.shift.client.model.listener;

import ru.shift.client.model.event.DisconnectEvent;

public interface DisconnectEventListener extends ModelEventListener {
    void onDisconnect(DisconnectEvent event);
}
