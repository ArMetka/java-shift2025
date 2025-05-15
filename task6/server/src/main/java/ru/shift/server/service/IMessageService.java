package ru.shift.server.service;

import ru.shift.common.ClientMessage;
import ru.shift.common.ServerMessage;
import ru.shift.server.context.UserContext;

import java.io.IOException;

public interface IMessageService {
    void addUser(UserContext ctx);

    void removeUser(UserContext ctx);

    ClientMessage readMessage(UserContext ctx) throws IOException;

    void sendMessage(UserContext ctx, ServerMessage msg) throws IOException;

    void broadcastMessage(ServerMessage msg);
}
