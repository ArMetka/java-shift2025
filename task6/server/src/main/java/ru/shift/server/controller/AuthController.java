package ru.shift.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.dto.ErrorData;
import ru.shift.common.dto.TokenData;
import ru.shift.common.dto.UserData;
import ru.shift.common.messages.*;
import ru.shift.server.context.SessionManager;
import ru.shift.server.context.UserContext;
import ru.shift.server.exception.UnsupportedMessageTypeException;
import ru.shift.server.service.IMessageService;

import java.io.IOException;
import java.util.List;

public class AuthController implements Controller {
    private static final List<MessageType> acceptedTypes;
    private static final Logger log = LogManager.getLogger(AuthController.class);

    static {
        acceptedTypes = List.of(
                MessageType.AUTH_REQUEST
        );
    }

    @Override
    public List<MessageType> getAcceptedMessageTypes() {
        return acceptedTypes;
    }

    @Override
    public void process(UserContext userContext, ClientMessage clientMessage, IMessageService messageService) throws IOException {
        if (!acceptedTypes.contains(clientMessage.getType())) {
            throw new UnsupportedMessageTypeException("message type is not supported: " + clientMessage.getType());
        }

        switch (clientMessage.getType()) {
            case AUTH_REQUEST -> authRequestHandler(
                    userContext,
                    (AuthRequest) clientMessage,
                    messageService
            );
        }
    }

    private void authRequestHandler(UserContext userContext, AuthRequest message, IMessageService messageService) throws IOException {
        try {
            var token = SessionManager.addUser(message.userData().name());
            userContext.setUsername(message.userData().name());
            userContext.setSessionToken(token);
            userContext.setAuthorized(true);
            messageService.sendMessage(
                    userContext,
                    new AuthSuccess(new TokenData(token))
            );
            messageService.broadcastMessage(new UserJoin(
                    new UserData(userContext.getUsername())
            ));
            messageService.sendMessage(
                    userContext,
                    new UserList(SessionManager.getUsers())
            );
            log.info("authorization of user \"{}\" complete", userContext.getUsername());
        } catch (RuntimeException e) {
            messageService.sendMessage(
                    userContext,
                    new AuthFail(new ErrorData(e.getMessage()))
            );
        }
    }
}
