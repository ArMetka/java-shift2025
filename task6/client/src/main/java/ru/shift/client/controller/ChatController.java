package ru.shift.client.controller;

import ru.shift.client.model.IChatModel;
import ru.shift.client.view.IChatView;
import ru.shift.client.view.enums.ViewEventType;
import ru.shift.client.view.event.ConnectEvent;
import ru.shift.client.view.event.DisconnectEvent;
import ru.shift.client.view.event.SendMessageEvent;
import ru.shift.client.view.listener.ConnectEventListener;
import ru.shift.client.view.listener.DisconnectEventListener;
import ru.shift.client.view.listener.SendMessageEventListener;
import ru.shift.common.ServerMessage;
import ru.shift.common.messages.*;

public class ChatController implements IChatController {
    private final IChatModel model;
    private final IChatView view;

    public ChatController(IChatModel model, IChatView view) {
        this.model = model;
        this.view = view;
        subscribeToView();
    }

    private void subscribeToView() {
        view.addViewEventListener(ViewEventType.CONNECT, (ConnectEventListener) this::handleConnect);
        view.addViewEventListener(ViewEventType.DISCONNECT, (DisconnectEventListener) this::handleDisconnect);
        view.addViewEventListener(ViewEventType.SEND_MESSAGE, (SendMessageEventListener) this::handleSendMessage);
    }

    private void handleConnect(ConnectEvent event) {
        model.initConnection(event.username(), event.address(), event.port());
    }

    private void handleDisconnect(DisconnectEvent event) {
        model.disconnect();
    }

    private void handleSendMessage(SendMessageEvent event) {
        model.sendMessage(event.message());
    }

    @Override
    public void handleServerMessage(ServerMessage msg) {
        switch (msg.getType()) {
            case AUTH_FAIL -> handleConnectionFail(((AuthFail) msg).errorData().description());
            case AUTH_SUCCESS -> model.establishConnection(((AuthSuccess) msg).tokenData().token());
            case CHAT_MESSAGE -> model.addMessage(((ChatMessage) msg).chatMessageData());
            case USER_JOIN -> model.addUser(((UserJoin) msg).userData());
            case USER_LEAVE -> model.removeUser(((UserLeave) msg).userData());
            case USER_LIST -> model.updateUserList(((UserList) msg).users());
            case DISCONNECT -> model.disconnect();
        }
    }

    @Override
    public void handleConnectionFail(String description) {
        model.failConnection(description);
    }
}
