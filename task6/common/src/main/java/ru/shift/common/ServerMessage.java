package ru.shift.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.shift.common.messages.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "message_type")
@JsonSubTypes({
        @Type(value = UserJoin.class, name = "user_join"),
        @Type(value = UserLeave.class, name = "user_leave"),
        @Type(value = UserList.class, name = "user_list"),
        @Type(value = ChatMessage.class, name = "chat_message"),
        @Type(value = AuthSuccess.class, name = "auth_success"),
        @Type(value = AuthFail.class, name = "auth_fail"),
        @Type(value = Disconnect.class, name = "disconnect")
})
public interface ServerMessage extends Message {
}
