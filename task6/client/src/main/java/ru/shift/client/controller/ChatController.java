package ru.shift.client.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.client.model.IChatModel;
import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.listener.ConnectionFailEventListener;
import ru.shift.client.view.IChatView;
import ru.shift.client.view.enums.ViewEventType;
import ru.shift.client.view.event.ConnectEvent;
import ru.shift.client.view.event.DisconnectEvent;
import ru.shift.client.view.event.SendMessageEvent;
import ru.shift.client.view.listener.ConnectEventListener;
import ru.shift.client.view.listener.DisconnectEventListener;
import ru.shift.client.view.listener.SendMessageEventListener;
import ru.shift.common.connection.ChatConnection;
import ru.shift.common.message.impl.ChatMessageNotification;
import ru.shift.common.message.impl.JsonMessageMapper;
import ru.shift.common.message.impl.UserJoinNotification;
import ru.shift.common.message.impl.UserLeaveNotification;

import java.net.Socket;

public class ChatController {
    private static final Logger log = LogManager.getLogger(ChatController.class);
    private final IChatModel model;
    private final Object lock = new Object();
    private boolean listening = false;

    public ChatController(IChatModel model, IChatView view) {
        this.model = model;
        view.addViewEventListener(ViewEventType.CONNECT, (ConnectEventListener) this::handleConnect);
        view.addViewEventListener(ViewEventType.DISCONNECT, (DisconnectEventListener) this::handleDisconnect);
        view.addViewEventListener(ViewEventType.SEND_MESSAGE, (SendMessageEventListener) this::handleSendMessage);
    }

    private void handleConnect(ConnectEvent event) {
        synchronized (lock) {
            new Thread(() -> {
                try (var socket = new Socket(event.address(), event.port());
                     var connection = new ChatConnection(socket, new JsonMessageMapper())
                ) {
                    model.addModelEventListener(ModelEventType.CONNECTION_FAIL, (ConnectionFailEventListener) e -> {
                        try {
                            connection.close();
                        } catch (Exception ex) {
                            log.error("connection failed", ex);
                        }
                    });
                    model.setConnection(connection);
                    listening = true;
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    listenMessages(connection);
                } catch (Exception e) {
                    log.error("failed to connect", e);
                    model.failConnection(e.getMessage());
                } finally {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }).start();
            try {
                lock.wait();
            } catch (InterruptedException ignore) {
            }
        }
        if (listening) {
            model.initConnection(event.username());
        }
    }

    private void listenMessages(ChatConnection connection) {
        try {
            connection.readMessagesLoop((msg) -> {
                switch (msg.getType()) {
                    case CHAT_MESSAGE_NTF -> {
                        var chatMsg = (ChatMessageNotification) msg;
                        model.addMessage(chatMsg.getUsername(), chatMsg.getChatMessage(), chatMsg.getDate());
                    }
                    case USER_JOIN_NTF -> model.addUser(((UserJoinNotification) msg).getUsername());
                    case USER_LEAVE_NTF -> model.removeUser(((UserLeaveNotification) msg).getUsername());
                    case DISCONNECT_NTF -> model.disconnect(true);
                    default -> log.warn("received unexpected message of type {}", msg.getType());
                }
            });
        } catch (Exception e) {
            log.error("stopped listening", e);
        } finally {
            listening = false;
        }
    }

    private void handleDisconnect(DisconnectEvent event) {
        model.disconnect(false);
    }

    private void handleSendMessage(SendMessageEvent event) {
        model.sendMessage(event.message());
    }
}
