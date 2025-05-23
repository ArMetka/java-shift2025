package ru.shift.common.message;

public interface Notification extends Message {
    @Override
    default MessageCategory getCategory() {
        return MessageCategory.NOTIFICATION;
    }
}
