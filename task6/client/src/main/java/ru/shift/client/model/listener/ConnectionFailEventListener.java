package ru.shift.client.model.listener;

import ru.shift.client.model.event.ConnectionFailEvent;

public interface ConnectionFailEventListener extends ModelEventListener {
    void onConnectionFail(ConnectionFailEvent event);
}
