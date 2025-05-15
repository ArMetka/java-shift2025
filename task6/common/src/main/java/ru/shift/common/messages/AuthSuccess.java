package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.TokenData;

@JsonTypeName("auth_success")
public record AuthSuccess(
        TokenData tokenData
) implements ServerMessage {

    @Override
    public MessageType getType() {
        return MessageType.AUTH_SUCCESS;
    }
}
