package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.ErrorData;

@JsonTypeName("auth_fail")
public record AuthFail(
        ErrorData errorData
) implements ServerMessage {

    @Override
    public MessageType getType() {
        return MessageType.AUTH_FAIL;
    }
}
