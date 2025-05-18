package ru.shift.server.context;

import ru.shift.server.exception.InvalidUserException;
import ru.shift.server.exception.UsernameAlreadyTakenException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UserManager {
    private static final Set<String> users;
    private static final Set<String> forbiddenUserNames;

    static {
        users = new HashSet<>();
        forbiddenUserNames = Set.of(
                "server"
        );
    }

    public static void addUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidUserException("username is empty");
        }

        if (forbiddenUserNames.contains(username.toLowerCase())) {
            throw new InvalidUserException("username is unavailable: " + username);
        }

        synchronized (users) {
            if (!users.add(username.toLowerCase())) {
                throw new UsernameAlreadyTakenException("username already taken by another user: " + username);
            }
        }
    }

    public static boolean removeUser(String username) {
        if (username == null) {
            return false;
        }

        synchronized (users) {
            return users.remove(username.toLowerCase());
        }
    }

    public static List<String> getUsers() {
        synchronized (users) {
            return new ArrayList<>(users);
        }
    }
}
