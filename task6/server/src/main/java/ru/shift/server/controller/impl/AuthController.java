package ru.shift.server.controller.impl;

import ru.shift.common.message.Message;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.impl.AuthRequest;
import ru.shift.common.message.impl.UserJoinNotification;
import ru.shift.server.context.UserContext;
import ru.shift.server.context.UserManager;
import ru.shift.server.controller.Controller;
import ru.shift.server.exception.UnsupportedMessageTypeException;

import java.util.List;

public class AuthController implements Controller {
    private static final List<MessageType> acceptedTypes;

    static {
        acceptedTypes = List.of(
                MessageType.AUTH_REQ,
                MessageType.DISCONNECT_NTF
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

        switch (msg.getType()) {
            case AUTH_REQ -> handleAuthReq(ctx, (AuthRequest) msg);
            case DISCONNECT_NTF -> handleDisconnectNtf(ctx);
        }
    }

    private void handleAuthReq(UserContext ctx, AuthRequest req) {
        if (ctx.getUsername() != null) {
            ctx.getConnection().sendMessage(req.error("already authorized"));
            return;
        }

        try {
            UserManager.addUser(req.getUsername());
            ctx.setUsername(req.getUsername());
        } catch (Exception e) {
            ctx.getConnection().sendMessage(req.error(e.getMessage()));
            return;
        }

        ctx.getConnection().sendMessage(req.success());
        ctx.getBroadcastService().addUser(ctx);
        ctx.getBroadcastService().broadcast(new UserJoinNotification(ctx.getUsername()));
    }

    private void handleDisconnectNtf(UserContext ctx) {
        ctx.setDisconnected(true);
        ctx.getConnection().setShouldListen(false);
    }
}
