package ru.shift.client.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.client.model.enums.ConnectionState;
import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.event.*;
import ru.shift.client.model.exception.RequestFailedException;
import ru.shift.client.model.listener.*;
import ru.shift.common.connection.ChatConnection;
import ru.shift.common.message.impl.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class ChatModel implements IChatModel {
    private static final Logger log = LogManager.getLogger(ChatModel.class);
    private final EnumMap<ModelEventType, List<ModelEventListener>> listeners;
    private volatile ChatConnection connection;
    private volatile String username;
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
    public void initConnection(String username) {
        if (connectionState != ConnectionState.NOT_CONNECTED) {
            return;
        }

        this.username = username;
        var future = connection.sendRequestAsync(new AuthRequest(username));
        AuthResponse response;
        try {
            response = (AuthResponse) future.get();
        } catch (Exception e) {
            log.error("failed to get auth response", e);
            failConnection(e.getMessage());
            return;
        }
        if (response.getError() != null) {
            failConnection(response.getError());
            return;
        }
        log.info("successfully connected");
        connectionState = ConnectionState.CONNECTED;
        notifyConnectionSuccessEventListeners(new ConnectionSuccessEvent());

        notifyUserListEventListeners(new UserListEvent(requestUsers()));
    }

    @Override
    public void setConnection(ChatConnection connection) {
        this.connection = connection;
    }

    @Override
    public void failConnection(String description) {
        if (connectionState != ConnectionState.NOT_CONNECTED) {
            return;
        }

        username = null;
        connection = null;
        notifyConnectionFailEventListeners(new ConnectionFailEvent(description));
    }

    @Override
    public void sendMessage(String message) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        connection.sendMessage(new ChatMessageNotification(message, username, new Date()));
    }

    @Override
    public void disconnect(boolean isServerDisconnect) {
        if (connectionState != ConnectionState.CONNECTED) {
            return;
        }

        if (!isServerDisconnect) {
            connection.sendMessage(new DisconnectNotification());
        }
        username = null;
        connection = null;
        connectionState = ConnectionState.NOT_CONNECTED;
        notifyDisconnectEventListeners(new DisconnectEvent());
    }

    @Override
    public void addUser(String username) {
        notifyUserJoinEventListeners(new UserJoinEvent(username, new Date()));
    }

    @Override
    public void removeUser(String username) {
        notifyUserLeaveEventListeners(new UserLeaveEvent(username, new Date()));
    }

    @Override
    public void addMessage(String username, String message, Date date) {
        notifyNewMessageEventListeners(new NewMessageEvent(username, message, date));
    }

    private List<String> requestUsers() {
        var future = connection.sendRequestAsync(new UserListRequest());

        try {
            UserListResponse res = (UserListResponse) future.get();
            if (res.getError() != null) {
                throw new RequestFailedException(res.getError());
            }
            return res.getUsers();
        } catch (Exception e) {
            log.error("failed to get user list", e);
            return null;
        }
    }

    private void notifyConnectionSuccessEventListeners(ConnectionSuccessEvent event) {
        for (var listener : listeners.get(ModelEventType.CONNECTION_SUCCESS)) {
            ((ConnectionSuccessEventListener) listener).onConnectionSuccess(event);
        }
    }

    private void notifyConnectionFailEventListeners(ConnectionFailEvent event) {
        for (var listener : listeners.get(ModelEventType.CONNECTION_FAIL)) {
            ((ConnectionFailEventListener) listener).onConnectionFail(event);
        }
    }

    private void notifyDisconnectEventListeners(DisconnectEvent event) {
        for (var listener : listeners.get(ModelEventType.DISCONNECT)) {
            ((DisconnectEventListener) listener).onDisconnect(event);
        }
    }

    private void notifyNewMessageEventListeners(NewMessageEvent event) {
        for (var listener : listeners.get(ModelEventType.NEW_MESSAGE)) {
            ((NewMessageEventListener) listener).onNewMessage(event);
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
