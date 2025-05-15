package ru.shift.client.model.event;

import ru.shift.common.dto.UserData;

import java.util.List;

public record UserListEvent(
        List<UserData> userDataList
) {
}
