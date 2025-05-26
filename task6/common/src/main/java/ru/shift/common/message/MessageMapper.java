package ru.shift.common.message;

public interface MessageMapper {
    String serialize(Message msg);

    Message deserialize(String msg);
}
