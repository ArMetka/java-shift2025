package ru.shift.client.model.event;

import ru.shift.common.dto.UserData;

import java.util.Date;

public record UserJoinEvent(
        UserData userData,
        Date date
) {
}
