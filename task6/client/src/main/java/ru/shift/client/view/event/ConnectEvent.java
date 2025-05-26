package ru.shift.client.view.event;

public record ConnectEvent(
        String username,
        String address,
        int port
) {
}
