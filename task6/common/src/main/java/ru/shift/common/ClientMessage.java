package ru.shift.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.shift.common.dto.TokenData;
import ru.shift.common.messages.AuthRequest;
import ru.shift.common.messages.ChatMessage;
import ru.shift.common.messages.Disconnect;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "message_type")
@JsonSubTypes({
        @Type(value = ChatMessage.class, name = "chat_message"),
        @Type(value = AuthRequest.class, name = "auth_request"),
        @Type(value = Disconnect.class, name = "disconnect")
})
public interface ClientMessage extends Message {
    @JsonIgnore
    TokenData getSessionToken();
}
