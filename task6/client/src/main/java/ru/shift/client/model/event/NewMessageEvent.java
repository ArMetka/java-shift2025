package ru.shift.client.model.event;

import java.util.Date;

public record NewMessageEvent(
        String username,
        String message,
        Date date
) {
}
