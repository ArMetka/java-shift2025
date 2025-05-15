package ru.shift.server.controller;

import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.dto.ChatMessageData;
import ru.shift.common.dto.UserData;
import ru.shift.common.messages.ChatMessage;
import ru.shift.server.context.UserContext;
import ru.shift.server.exception.UnsupportedMessageTypeException;
import ru.shift.server.service.IMessageService;

import java.util.List;

public class MessageController implements Controller {
    private static final List<MessageType> acceptedTypes;

    static {
        acceptedTypes = List.of(
                MessageType.CHAT_MESSAGE
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
            case CHAT_MESSAGE -> handleChatMessage(
                    userContext,
                    (ChatMessage) clientMessage,
                    messageService
            );
        }
    }

    private void handleChatMessage(UserContext userContext, ChatMessage clientMessage, IMessageService messageService) {
        messageService.broadcastMessage(new ChatMessage(
                null,
                new ChatMessageData(
                        new UserData(userContext.getUsername()),
                        clientMessage.chatMessageData().messageContent(),
                        clientMessage.chatMessageData().date()
                )
        ));
    }
}
