package ru.shift.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.Date;

@JsonTypeName("chat_message_data")
public record ChatMessageData(
        UserData userData,
        String messageContent,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC") Date date
) implements Serializable {
}
