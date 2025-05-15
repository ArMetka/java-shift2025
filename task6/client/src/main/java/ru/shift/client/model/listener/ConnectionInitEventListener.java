package ru.shift.client.model.listener;

import ru.shift.client.model.event.ConnectionInitEvent;

public interface ConnectionInitEventListener extends ModelEventListener {
    void onConnectionInit(ConnectionInitEvent event);
}
