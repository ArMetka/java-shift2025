package ru.shift.client.model;

import ru.shift.common.dto.ChatMessageData;
import ru.shift.common.dto.UserData;

import java.util.List;

public interface IChatModel extends IModelEventPublisher {
    void sendMessage(String message);

    void addMessage(ChatMessageData messageData);

    void addUser(UserData userData);

    void removeUser(UserData userData);

    void updateUserList(List<UserData> userDataList);

    void initConnection(String username, String address, int port);

    void establishConnection(String sessionToken);

    void failConnection(String description);

    void disconnect();
}
