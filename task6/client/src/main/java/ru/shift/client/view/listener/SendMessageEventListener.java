package ru.shift.client.view.listener;

import ru.shift.client.view.event.SendMessageEvent;

public interface SendMessageEventListener extends ViewEventListener {
    void onSendMessage(SendMessageEvent event);
}
