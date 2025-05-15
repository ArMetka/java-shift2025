package ru.shift.server.controller;

import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.server.context.UserContext;
import ru.shift.server.exception.UnsupportedMessageTypeException;
import ru.shift.server.service.IMessageService;

import java.util.List;

public class ConnectionController implements Controller {
    private static final List<MessageType> acceptedTypes;

    static {
        acceptedTypes = List.of(
                MessageType.DISCONNECT
        );
    }

    @Override
    public List<MessageType> getAcceptedMessageTypes() {
        return acceptedTypes;
    }

    @Override
    public void process(UserContext userContext, ClientMessage clientMessage, IMessageService messageService) {
        if (!acceptedTypes.contains(clientMessage.getType())) {
            throw new UnsupportedMessageTypeException("message type is not supported: " + clientMessage.getType());
        }

        switch (clientMessage.getType()) {
            case DISCONNECT -> handleDisconnect(
                    userContext
            );
        }
    }

    private void handleDisconnect(UserContext userContext) {
        userContext.setDisconnected(true);
    }
}
