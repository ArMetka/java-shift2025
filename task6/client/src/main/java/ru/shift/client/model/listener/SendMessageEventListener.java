package ru.shift.client.model.listener;

import ru.shift.client.model.event.SendMessageEvent;

public interface SendMessageEventListener extends ModelEventListener {
    void onSendMessage(SendMessageEvent event);
}
