package ru.shift.client.model.event;

import java.util.Date;

public record UserLeaveEvent(
        String username,
        Date date
) {
}
