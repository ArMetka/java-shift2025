package ru.shift.client.model;

import ru.shift.common.connection.ChatConnection;

import java.util.Date;

public interface IChatModel extends IModelEventPublisher {
    void sendMessage(String message);

    void initConnection(String username);

    void setConnection(ChatConnection connection);

    void failConnection(String description);

    void disconnect(boolean isServerDisconnect);

    void addUser(String username);

    void removeUser(String username);

    void addMessage(String username, String message, Date date);
}
