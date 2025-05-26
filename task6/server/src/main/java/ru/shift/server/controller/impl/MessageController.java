package ru.shift.server.controller.impl;

import ru.shift.common.message.Message;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.impl.ChatMessageNotification;
import ru.shift.server.context.UserContext;
import ru.shift.server.controller.Controller;
import ru.shift.server.exception.UnsupportedMessageTypeException;

import java.util.List;

public class MessageController implements Controller {
    private static final List<MessageType> acceptedTypes;

    static {
        acceptedTypes = List.of(
                MessageType.CHAT_MESSAGE_NTF
        );
    }

    @Override
    public List<MessageType> getAcceptedMessageTypes() {
        return acceptedTypes;
    }

    @Override
    public void process(UserContext ctx, Message msg) {
        if (!acceptedTypes.contains(msg.getType())) {
            throw new UnsupportedMessageTypeException("message type unsupported by controller: " + msg.getType().name());
        }

        handleChatMessageNtf(ctx, (ChatMessageNotification) msg);
    }

    private void handleChatMessageNtf(UserContext ctx, ChatMessageNotification ntf) {
        if (ctx.getUsername() == null) { // not authorized
            return;
        }

        ctx.getBroadcastService().broadcast(ntf);
    }
}
