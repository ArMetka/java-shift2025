package ru.shift.common.message;

public interface Notification extends Message {
    @Override
    default boolean isRequest() {
        return false;
    }

    @Override
    default boolean isResponse() {
        return false;
    }

    @Override
    default boolean isNotification() {
        return true;
    }
}
