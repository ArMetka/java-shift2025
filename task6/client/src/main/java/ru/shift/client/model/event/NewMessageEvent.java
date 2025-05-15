package ru.shift.client.model.event;

import ru.shift.common.dto.ChatMessageData;

public record NewMessageEvent(
        ChatMessageData chatMessageData
) {
}
