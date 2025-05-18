package ru.shift.common.message.impl;

import ru.shift.common.message.MessageType;
import ru.shift.common.message.Request;

import java.util.List;

public class UserListRequest implements Request {
    private String id;

    public UserListRequest() {
        id = Request.generateID();
    }

    public UserListResponse success(List<String> users) {
        return new UserListResponse(id, users);
    }

    public UserListResponse error(String description) {
        return new UserListResponse(id, description);
    }

    @Override
    public MessageType getType() {
        return MessageType.USER_LIST_REQ;
    }

    @Override
    public String getID() {
        return id;
    }
}
