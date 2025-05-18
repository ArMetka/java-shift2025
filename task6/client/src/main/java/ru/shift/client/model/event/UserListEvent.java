package ru.shift.client.model.event;

import java.util.List;

public record UserListEvent(
        List<String> userList
) {
}
