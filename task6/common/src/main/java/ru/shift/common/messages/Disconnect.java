package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.TokenData;

@JsonTypeName("disconnect")
public record Disconnect(
        TokenData tokenData
) implements ServerMessage, ClientMessage {

    @Override
    public TokenData getSessionToken() {
        return tokenData;
    }

    @Override
    public MessageType getType() {
        return MessageType.DISCONNECT;
    }
}
