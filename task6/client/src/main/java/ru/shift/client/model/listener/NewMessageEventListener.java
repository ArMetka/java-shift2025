package ru.shift.client.model.listener;

import ru.shift.client.model.event.NewMessageEvent;

public interface NewMessageEventListener extends ModelEventListener {
    void onNewMessage(NewMessageEvent event);
}
