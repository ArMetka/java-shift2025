package ru.shift.common;

import java.io.Serializable;

public enum MessageType implements Serializable {
    USER_JOIN,    // User joined chat               (server message)
    USER_LEAVE,   // User left chat                 (server message)
    USER_LIST,    // List of users                  (server & client message)
    CHAT_MESSAGE, // New chat message               (server & client message)
    AUTH_REQUEST, // Authentification request       (client message)
    AUTH_SUCCESS, // Successful authentification    (server message)
    AUTH_FAIL,    // Authentification failed        (server message)
    DISCONNECT,   // Disconnect                     (server & client message)
}
