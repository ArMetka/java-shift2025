package ru.shift.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.message.Notification;
import ru.shift.server.context.UserContext;

import java.util.HashSet;
import java.util.Set;

public class BroadcastService {
    private static final Logger log = LogManager.getLogger(BroadcastService.class);
    private final Set<UserContext> users = new HashSet<>();

    public boolean addUser(UserContext ctx) {
        if (ctx == null) {
            return false;
        }

        synchronized (users) {
            return users.add(ctx);
        }
    }

    public boolean removeUser(UserContext ctx) {
        if (ctx == null) {
            return false;
        }

        synchronized (users) {
            return users.remove(ctx);
        }
    }

    public void broadcast(Notification msg) {
        synchronized (users) {
            for (var user : users) {
                try {
                    user.getConnection().sendMessage(msg);
                } catch (Exception e) {
                    log.warn("failed to send message to client {}", user.getUsername(), e);
                    user.setDisconnected(true);
                    removeUser(user);
                }
            }
        }
    }
}
