package ru.shift.server.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.ClientMessage;
import ru.shift.common.ServerMessage;
import ru.shift.server.context.UserContext;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MessageService implements IMessageService {
    private static final Logger log = LogManager.getLogger(MessageService.class);
    private final List<UserContext> users;
    private final ObjectMapper mapper;

    public MessageService() {
        users = new LinkedList<>();
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public void addUser(UserContext ctx) {
        synchronized (users) {
            users.add(ctx);
        }
    }

    @Override
    public void removeUser(UserContext ctx) {
        synchronized (users) {
            users.remove(ctx);
        }
    }

    @Override
    public ClientMessage readMessage(UserContext ctx) throws IOException {
        synchronized (ctx.getIn()) {
            return mapper.readValue(ctx.getIn(), ClientMessage.class);
        }
    }

    @Override
    public void sendMessage(UserContext ctx, ServerMessage msg) throws IOException {
        synchronized (ctx.getOut()) {
            log.trace("sending message {} to user {}", msg.getType().name(), ctx.getUsername());
            mapper.writeValue(ctx.getOut(), msg);
        }
    }

    @Override
    public void broadcastMessage(ServerMessage msg) {
        synchronized (users) {
            for (var user : users) {
                if (!user.isAuthorized()) {
                    continue;
                }
                try {
                    sendMessage(user, msg);
                } catch (Exception e) {
                    log.error("failed to send message to user {}: {}", user.getUsername(), e.getMessage());
                    removeUser(user);
                    user.setDisconnected(true);
                }
            }
        }
    }
}
