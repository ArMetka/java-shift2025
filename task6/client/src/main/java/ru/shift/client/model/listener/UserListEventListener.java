package ru.shift.client.model.listener;

import ru.shift.client.model.event.UserListEvent;

public interface UserListEventListener extends ModelEventListener {
    void onUserList(UserListEvent event);
}
