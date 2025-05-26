package ru.shift.common.message.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.Notification;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageNotification implements Notification {
    @Getter
    private String chatMessage;
    @Getter
    private String username;
    @Getter
    private Date date;

    @Override
    public MessageType getType() {
        return MessageType.CHAT_MESSAGE_NTF;
    }
}
