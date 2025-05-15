package ru.shift.server.controller;

import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.server.context.UserContext;
import ru.shift.server.service.IMessageService;

import java.io.IOException;
import java.util.List;

public interface Controller {
    List<MessageType> getAcceptedMessageTypes();

    void process(UserContext userContext, ClientMessage clientMessage, IMessageService messageService) throws IOException;
}
