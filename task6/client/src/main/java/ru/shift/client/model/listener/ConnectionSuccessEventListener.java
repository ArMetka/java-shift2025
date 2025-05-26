package ru.shift.client.model.listener;

import ru.shift.client.model.event.ConnectionSuccessEvent;

public interface ConnectionSuccessEventListener extends ModelEventListener {
    void onConnectionSuccess(ConnectionSuccessEvent event);
}
