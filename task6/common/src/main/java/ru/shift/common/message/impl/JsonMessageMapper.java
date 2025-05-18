package ru.shift.common.message.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import ru.shift.common.exception.MappingException;
import ru.shift.common.message.*;

public class JsonMessageMapper implements MessageMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        mapper.registerSubtypes(new NamedType(Request.class, "REQUEST"));
        mapper.registerSubtypes(new NamedType(Response.class, "RESPONSE"));
        mapper.registerSubtypes(new NamedType(Notification.class, "NOTIFICATION"));
        mapper.registerSubtypes(new NamedType(AuthRequest.class, MessageType.AUTH_REQ.name()));
        mapper.registerSubtypes(new NamedType(AuthResponse.class, MessageType.AUTH_RES.name()));
        mapper.registerSubtypes(new NamedType(ChatMessageNotification.class, MessageType.CHAT_MESSAGE_NTF.name()));
        mapper.registerSubtypes(new NamedType(DisconnectNotification.class, MessageType.DISCONNECT_NTF.name()));
        mapper.registerSubtypes(new NamedType(UserJoinNotification.class, MessageType.USER_JOIN_NTF.name()));
        mapper.registerSubtypes(new NamedType(UserLeaveNotification.class, MessageType.USER_LEAVE_NTF.name()));
        mapper.registerSubtypes(new NamedType(UserListRequest.class, MessageType.USER_LIST_REQ.name()));
        mapper.registerSubtypes(new NamedType(UserListResponse.class, MessageType.USER_LIST_RES.name()));
    }

    @Override
    public String serialize(Message msg) {
        try {
            return mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            throw new MappingException("failed to serialize message", e);
        }
    }

    @Override
    public Message deserialize(String msg) {
        try {
            return mapper.readValue(msg, Message.class);
        } catch (JsonProcessingException e) {
            throw new MappingException("failed to deserialize message", e);
        }
    }
}
