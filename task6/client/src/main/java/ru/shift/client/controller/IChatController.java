package ru.shift.client.controller;

import ru.shift.common.ServerMessage;

public interface IChatController {
    void handleServerMessage(ServerMessage msg);

    void handleConnectionFail(String description);
}
