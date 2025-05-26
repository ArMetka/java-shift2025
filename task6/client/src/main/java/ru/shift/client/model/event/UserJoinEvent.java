package ru.shift.client.model.event;

import java.util.Date;

public record UserJoinEvent(
        String username,
        Date date
) {
}
