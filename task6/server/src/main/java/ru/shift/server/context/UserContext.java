package ru.shift.server.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.shift.common.connection.ChatConnection;
import ru.shift.common.message.impl.UserLeaveNotification;
import ru.shift.server.service.BroadcastService;

@RequiredArgsConstructor
public class UserContext implements AutoCloseable {
    @Getter
    private final ChatConnection connection;
    @Getter
    private final BroadcastService broadcastService;

    @Getter
    @Setter
    private String username = null;
    @Getter
    @Setter
    private boolean disconnected = false;

    @Override
    public void close() throws Exception {
        if (username != null) {
            UserManager.removeUser(username);
        }
        if (broadcastService != null) {
            broadcastService.removeUser(this);
        }
        if (broadcastService != null && username != null) {
            broadcastService.broadcast(new UserLeaveNotification(username));
        }
        if (connection != null) {
            connection.close();
        }
    }
}
