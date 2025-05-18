package ru.shift.common.message.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.Notification;

@AllArgsConstructor
@NoArgsConstructor
public class UserLeaveNotification implements Notification {
    @Getter
    private String username;

    @Override
    public MessageType getType() {
        return MessageType.USER_LEAVE_NTF;
    }
}
