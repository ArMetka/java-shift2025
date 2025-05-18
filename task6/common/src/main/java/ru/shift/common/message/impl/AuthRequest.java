package ru.shift.common.message.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.Request;

@NoArgsConstructor
public class AuthRequest implements Request {
    private String id;
    @Getter
    private String username;

    public AuthRequest(String username) {
        id = Request.generateID();
        this.username = username;
    }

    public AuthResponse success() {
        return new AuthResponse(id);
    }

    public AuthResponse error(String description) {
        return new AuthResponse(id, description);
    }

    @Override
    public MessageType getType() {
        return MessageType.AUTH_REQ;
    }

    @Override
    public String getID() {
        return id;
    }
}
