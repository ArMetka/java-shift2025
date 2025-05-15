package ru.shift.common.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.ServerMessage;
import ru.shift.common.dto.ChatMessageData;
import ru.shift.common.dto.TokenData;

@JsonTypeName("chat_message")
public record ChatMessage(
        TokenData tokenData,
        ChatMessageData chatMessageData
) implements ServerMessage, ClientMessage {

    @Override
    public TokenData getSessionToken() {
        return tokenData;
    }

    @Override
    public MessageType getType() {
        return MessageType.CHAT_MESSAGE;
    }
}
