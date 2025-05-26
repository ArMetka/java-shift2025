package ru.shift.server.controller;

import ru.shift.common.message.Message;
import ru.shift.common.message.MessageType;
import ru.shift.server.context.UserContext;

import java.util.List;

public interface Controller {
    List<MessageType> getAcceptedMessageTypes();

    void process(UserContext ctx, Message msg);
}
