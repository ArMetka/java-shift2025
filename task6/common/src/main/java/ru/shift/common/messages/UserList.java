package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.UserData;

import java.util.List;

@JsonTypeName("user_list")
public record UserList(
        List<UserData> users
) implements ServerMessage {

    @Override
    public MessageType getType() {
        return MessageType.USER_LIST;
    }
}
