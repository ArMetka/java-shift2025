package ru.shift.client.model.listener;

import ru.shift.client.model.event.UserLeaveEvent;

public interface UserLeaveEventListener extends ModelEventListener {
    void onUserLeave(UserLeaveEvent event);
}
