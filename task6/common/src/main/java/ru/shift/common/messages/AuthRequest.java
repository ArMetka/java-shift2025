package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.dto.TokenData;
import ru.shift.common.dto.UserData;

@JsonTypeName("auth_request")
public record AuthRequest(
        UserData userData
) implements ClientMessage {
    @Override
    public TokenData getSessionToken() {
        return null;
    }

    @Override
    public MessageType getType() {
        return MessageType.AUTH_REQUEST;
    }
}
