package ru.shift.client.model;

import ru.shift.client.model.enums.ConnectionState;
import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.event.*;
import ru.shift.client.model.listener.*;
import ru.shift.common.dto.ChatMessageData;
import ru.shift.common.dto.UserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class ChatModel implements IChatModel {
    private final EnumMap<ModelEventType, List<ModelEventListener>> listeners;
    private volatile String username;
    private volatile String sessionToken;
    private volatile ConnectionState connectionState = ConnectionState.NOT_CONNECTED;

    public ChatModel() {
        listeners = new EnumMap<>(ModelEventType.class);
        for (var type : ModelEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    @Override
    public void addModelEventListener(ModelEventType type, ModelEventListener listener) {
        listeners.get(type).add(listener);
    }

    @Override
    public void sendMessage(String message) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        assert (sessionToken != null);
        notifySendMessageEventListeners(new SendMessageEvent(new ChatMessageData(
                new UserData(username),
                message,
                new Date()
        ), sessionToken));
    }

    @Override
    public void addMessage(ChatMessageData messageData) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        notifyNewMessageEventListeners(new NewMessageEvent(messageData));
    }

    @Override
    public void addUser(UserData userData) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        notifyUserJoinEventListeners(new UserJoinEvent(userData, new Date()));
    }

    @Override
    public void removeUser(UserData userData) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        notifyUserLeaveEventListeners(new UserLeaveEvent(userData, new Date()));
    }

    @Override
    public void updateUserList(List<UserData> userDataList) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        notifyUserListEventListeners(new UserListEvent(userDataList));
    }

    @Override
    public void initConnection(String username, String address, int port) {
        if (connectionState != ConnectionState.NOT_CONNECTED) {
            return;
        }

        this.username = username;
        connectionState = ConnectionState.CONNECTING;
        notifyConnectionInitEventListeners(new ConnectionInitEvent(username, address, port));
    }

    @Override
    public void establishConnection(String sessionToken) {
        if (connectionState != ConnectionState.CONNECTING) {
            return;
        }

        this.sessionToken = sessionToken;
        connectionState = ConnectionState.CONNECTED;
        notifyConnectionSuccessEventListeners(new ConnectionSuccessEvent(sessionToken));
    }

    @Override
    public void failConnection(String description) {
        if (connectionState != ConnectionState.CONNECTING) {
            return;
        }

        username = null;
        connectionState = ConnectionState.NOT_CONNECTED;
        notifyConnectionFailEventListeners(new ConnectionFailEvent(description));
    }

    @Override
    public void disconnect() {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        username = null;
        sessionToken = null;
        connectionState = ConnectionState.NOT_CONNECTED;
        notifyDisconnectEventListeners(new DisconnectEvent());
    }

    private void notifySendMessageEventListeners(SendMessageEvent event) {
        for (var listener : listeners.get(ModelEventType.SEND_MESSAGE)) {
            ((SendMessageEventListener) listener).onSendMessage(event);
        }
    }

    private void notifyNewMessageEventListeners(NewMessageEvent event) {
        for (var listener : listeners.get(ModelEventType.NEW_MESSAGE)) {
            ((NewMessageEventListener) listener).onNewMessage(event);
        }
    }

    private void notifyConnectionInitEventListeners(ConnectionInitEvent event) {
        for (var listener : listeners.get(ModelEventType.CONNECTION_INIT)) {
            ((ConnectionInitEventListener) listener).onConnectionInit(event);
        }
    }

    private void notifyConnectionFailEventListeners(ConnectionFailEvent event) {
        for (var listener : listeners.get(ModelEventType.CONNECTION_FAIL)) {
            ((ConnectionFailEventListener) listener).onConnectionFail(event);
        }
    }

    private void notifyConnectionSuccessEventListeners(ConnectionSuccessEvent event) {
        for (var listener : listeners.get(ModelEventType.CONNECTION_SUCCESS)) {
            ((ConnectionSuccessEventListener) listener).onConnectionSuccess(event);
        }
    }

    private void notifyDisconnectEventListeners(DisconnectEvent event) {
        for (var listener : listeners.get(ModelEventType.DISCONNECT)) {
            ((DisconnectEventListener) listener).onDisconnect(event);
        }
    }

    private void notifyUserJoinEventListeners(UserJoinEvent event) {
        for (var listener : listeners.get(ModelEventType.USER_JOIN)) {
            ((UserJoinEventListener) listener).onUserJoin(event);
        }
    }

    private void notifyUserLeaveEventListeners(UserLeaveEvent event) {
        for (var listener : listeners.get(ModelEventType.USER_LEAVE)) {
            ((UserLeaveEventListener) listener).onUserLeave(event);
        }
    }

    private void notifyUserListEventListeners(UserListEvent event) {
        for (var listener : listeners.get(ModelEventType.USER_LIST)) {
            ((UserListEventListener) listener).onUserList(event);
        }
    }
}
