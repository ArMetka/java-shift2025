package ru.shift.common.message.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.common.message.MessageType;
import ru.shift.common.message.Response;

import java.util.List;

@NoArgsConstructor
public class UserListResponse implements Response {
    private String id;
    private String error;
    @Getter
    private List<String> users;

    public UserListResponse(String id, List<String> users) {
        this.id = id;
        this.users = users;
        error = null;
    }

    public UserListResponse(String id, String error) {
        this.id = id;
        this.error = error;
        users = null;
    }

    @Override
    public MessageType getType() {
        return MessageType.USER_LIST_RES;
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
