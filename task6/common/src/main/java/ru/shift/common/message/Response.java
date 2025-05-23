package ru.shift.common.message;

public interface Response extends Message {
    String getID();

    String getError();

    @Override
    default MessageCategory getCategory() {
        return MessageCategory.RESPONSE;
    }
}
