package ru.shift.client.view;

import ru.shift.client.model.IModelEventPublisher;
import ru.shift.client.model.enums.ModelEventType;
import ru.shift.client.model.listener.*;
import ru.shift.client.view.enums.ViewEventType;
import ru.shift.client.view.event.ConnectEvent;
import ru.shift.client.view.event.DisconnectEvent;
import ru.shift.client.view.event.SendMessageEvent;
import ru.shift.client.view.listener.ConnectEventListener;
import ru.shift.client.view.listener.DisconnectEventListener;
import ru.shift.client.view.listener.SendMessageEventListener;
import ru.shift.client.view.listener.ViewEventListener;
import ru.shift.client.view.window.ConnectWindow;
import ru.shift.client.view.window.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class ChatView implements IChatView {
    private final EnumMap<ViewEventType, List<ViewEventListener>> listeners;
    private final IModelEventPublisher model;

    private final MainWindow mainWindow;
    private final ConnectWindow connectWindow;

    public ChatView(IModelEventPublisher model) {
        listeners = new EnumMap<>(ViewEventType.class);
        for (var type : ViewEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
        this.model = model;

        mainWindow = new MainWindow();
        connectWindow = new ConnectWindow(mainWindow);

        mainWindow.setSendMessageEventListener(this::notifySendMessageEventListeners);
        connectWindow.setConnectEventListener(this::notifyConnectEventListeners);
        connectWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                mainWindow.setConnectEnabled(true);
            }
        });
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notifyDisconnectEventListeners(new DisconnectEvent());
            }
        });
        mainWindow.setConnectBtnAction(e -> {
            connectWindow.setVisible(true);
            mainWindow.setConnectEnabled(false);
        });

        subscribeToModel();
    }

    private void subscribeToModel() {
        model.addModelEventListener(ModelEventType.CONNECTION_SUCCESS, (ConnectionSuccessEventListener) e -> {
            connectWindow.setVisible(false);
//            mainWindow.setConnectEnabled(false);
            SwingUtilities.invokeLater(() -> mainWindow.setConnectEnabled(false));
            mainWindow.setSendMessageBtnEnabled(true);
        });
        model.addModelEventListener(ModelEventType.CONNECTION_FAIL, (ConnectionFailEventListener) e -> {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(connectWindow, e.description()));
            SwingUtilities.invokeLater(() -> mainWindow.setConnectEnabled(true));
        });
        model.addModelEventListener(ModelEventType.NEW_MESSAGE, (NewMessageEventListener) e -> {
            mainWindow.newMessage(e.username(), e.message(), e.date());
        });
        model.addModelEventListener(ModelEventType.USER_LIST, (UserListEventListener) e -> {
            mainWindow.setUsers(e.userList());
        });
        model.addModelEventListener(ModelEventType.USER_JOIN, (UserJoinEventListener) e -> {
            mainWindow.userJoin(e.username(), e.date());
        });
        model.addModelEventListener(ModelEventType.USER_LEAVE, (UserLeaveEventListener) e -> {
            mainWindow.userLeave(e.username(), e.date());
        });
    }

    @Override
    public void addViewEventListener(ViewEventType type, ViewEventListener listener) {
        listeners.get(type).add(listener);
    }

    @Override
    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }

    private void notifyConnectEventListeners(ConnectEvent event) {
        for (var listener : listeners.get(ViewEventType.CONNECT)) {
            ((ConnectEventListener) listener).onConnect(event);
        }
    }

    private void notifyDisconnectEventListeners(DisconnectEvent event) {
        for (var listener : listeners.get(ViewEventType.DISCONNECT)) {
            ((DisconnectEventListener) listener).onDisconnect(event);
        }
    }

    private void notifySendMessageEventListeners(SendMessageEvent event) {
        for (var listener : listeners.get(ViewEventType.SEND_MESSAGE)) {
            ((SendMessageEventListener) listener).onSendMessage(event);
        }
    }
}
