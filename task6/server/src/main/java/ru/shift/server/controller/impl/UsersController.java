package ru.shift.server.controller.impl;

import ru.shift.common.message.Message;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.impl.UserListRequest;
import ru.shift.server.context.UserContext;
import ru.shift.server.context.UserManager;
import ru.shift.server.controller.Controller;
import ru.shift.server.exception.UnsupportedMessageTypeException;

import java.util.List;

public class UsersController implements Controller {
    private static final List<MessageType> acceptedTypes;

    static {
        acceptedTypes = List.of(
                MessageType.USER_LIST_REQ
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

        handleUserListReq(ctx, (UserListRequest) msg);
    }

    private void handleUserListReq(UserContext ctx, UserListRequest req) {
        if (ctx.getUsername() == null) {
            ctx.getConnection().sendMessage(req.error("not authorized"));
            return;
        }

        ctx.getConnection().sendMessage(req.success(UserManager.getUsers()));
    }
}
