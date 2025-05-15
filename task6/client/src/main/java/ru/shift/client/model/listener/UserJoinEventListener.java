package ru.shift.client.model.listener;

import ru.shift.client.model.event.UserJoinEvent;

public interface UserJoinEventListener extends ModelEventListener {
    void onUserJoin(UserJoinEvent event);
}
