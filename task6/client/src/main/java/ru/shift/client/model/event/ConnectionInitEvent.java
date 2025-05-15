package ru.shift.client.model.event;

public record ConnectionInitEvent(
        String username,
        String address,
        int port
) {
}
