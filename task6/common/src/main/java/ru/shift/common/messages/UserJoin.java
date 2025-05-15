package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.UserData;

@JsonTypeName("user_join")
public record UserJoin(
        UserData userData
) implements ServerMessage {

    @Override
    public MessageType getType() {
        return MessageType.USER_JOIN;
    }
}
