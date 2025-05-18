package ru.shift.common.message;

public interface Response extends Message {
    String getID();

    String getError();

    @Override
    default boolean isRequest() {
        return false;
    }

    @Override
    default boolean isResponse() {
        return true;
    }

    @Override
    default boolean isNotification() {
        return false;
    }
}
