package ru.shift.common.message;

import java.util.UUID;

public interface Request extends Message {
    String getID();

    static String generateID() {
        return UUID.randomUUID().toString();
    }

    @Override
    default MessageCategory getCategory() {
        return MessageCategory.REQUEST;
    }
}
