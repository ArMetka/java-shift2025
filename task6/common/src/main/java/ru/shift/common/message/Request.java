package ru.shift.common.message;

import java.util.UUID;

public interface Request extends Message {
    String getID();

    static String generateID() {
        return UUID.randomUUID().toString();
    }

    @Override
    default boolean isRequest() {
        return true;
    }

    @Override
    default boolean isResponse() {
        return false;
    }

    @Override
    default boolean isNotification() {
        return false;
    }
}
