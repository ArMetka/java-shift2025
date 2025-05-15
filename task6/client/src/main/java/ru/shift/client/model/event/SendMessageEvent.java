package ru.shift.client.model.event;

import ru.shift.common.dto.ChatMessageData;

public record SendMessageEvent(
        ChatMessageData chatMessageData,
        String sessionToken
) {
}
