package ru.shift.client.model.event;

import ru.shift.common.dto.UserData;

import java.util.Date;

public record UserLeaveEvent(
        UserData userData,
        Date date
) {
}
