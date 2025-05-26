package ru.shift.common.message;

import java.io.Serializable;

/// - `*_REQ` -> request;
/// - `*_RES` -> response;
/// - `*_NTF` -> notification;
public enum MessageType implements Serializable {
    AUTH_REQ,
    AUTH_RES,
    USER_LIST_REQ,
    USER_LIST_RES,
    USER_JOIN_NTF,
    USER_LEAVE_NTF,
    CHAT_MESSAGE_NTF,
    DISCONNECT_NTF,
}
