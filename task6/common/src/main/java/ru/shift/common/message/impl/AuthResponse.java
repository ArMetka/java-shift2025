package ru.shift.common.message.impl;

import lombok.NoArgsConstructor;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.Response;

@NoArgsConstructor
public class AuthResponse implements Response {
    private String id;
    private String error;

    public AuthResponse(String id) {
        this.id = id;
        error = null;
    }

    public AuthResponse(String id, String error) {
        this.id = id;
        this.error = error;
    }

    @Override
    public MessageType getType() {
        return MessageType.AUTH_RES;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getError() {
        return error;
    }
}
