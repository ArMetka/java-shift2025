package ru.shift.common.message.impl;

import ru.shift.common.message.MessageType;
import ru.shift.common.message.Notification;

public class DisconnectNotification implements Notification {
    @Override
    public MessageType getType() {
        return MessageType.DISCONNECT_NTF;
    }
}
