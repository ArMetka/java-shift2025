package ru.shift.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.ClientMessage;
import ru.shift.common.MessageType;
import ru.shift.common.dto.ErrorData;
import ru.shift.common.dto.UserData;
import ru.shift.common.messages.AuthFail;
import ru.shift.common.messages.UserLeave;
import ru.shift.server.context.SessionManager;
import ru.shift.server.context.UserContext;
import ru.shift.server.controller.ControllerRegistry;
import ru.shift.server.dto.ServerConfig;
import ru.shift.server.exception.InvalidServerConfigException;
import ru.shift.server.service.IMessageService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Runnable {
    private static final Logger log = LogManager.getLogger(ChatServer.class);
    private final IMessageService messageService;
    private final int port;
    private final int maxThreads;

    public ChatServer(ServerConfig cfg, IMessageService messageService) {
        validateServerConfig(cfg);
        port = cfg.port();
        maxThreads = cfg.maxThreads();
        this.messageService = messageService;
    }

    private void validateServerConfig(ServerConfig cfg) {
        if (cfg.port() < 0 || cfg.port() > 65536) {
            throw new InvalidServerConfigException("port must be in range [0; 65636], got: " + cfg.port());
        }

        if (cfg.maxThreads() < 0) {
            throw new InvalidServerConfigException("number of threads must be >= 0, got: " + cfg.maxThreads());
        }
    }

    @Override
    public void run() {
        ExecutorService pool;
        if (maxThreads == 0) {
            pool = Executors.newCachedThreadPool();
        } else {
            pool = Executors.newFixedThreadPool(maxThreads);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("server started listening on port {}", serverSocket.getLocalPort());
            while (true) {
                var client = serverSocket.accept();
                log.info("new client connected: {}:{}", client.getInetAddress(), client.getPort());
                pool.submit(() -> {
                    try {
                        handleClient(new UserContext(client));
                    } catch (IOException e) {
                        log.error("failed to create user context: {}", e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            log.fatal("failed to start server: {}", e.getMessage());
        }
        pool.shutdownNow();
    }

    private void handleClient(UserContext ctx) {
        messageService.addUser(ctx);
        try (ctx) {
            while (!ctx.isDisconnected()) {
                ClientMessage msg = messageService.readMessage(ctx);
                log.debug("received new {} message", msg.getType().name());

                if (msg.getType() == MessageType.DISCONNECT) {
                    ctx.setDisconnected(true);
                    break;
                }

                if (!checkSessionToken(ctx, msg)) {
                    continue;
                }

                ControllerRegistry.findByMessageType(msg.getType())
                        .process(ctx, msg, messageService);
            }
            log.info("client disconnected: {}", ctx.getUsername());
        } catch (Exception e) {
            log.error("connection terminated: {}", e.getMessage());
        } finally {
            ctx.setAuthorized(false);
            if (ctx.getUsername() != null) {
                messageService.broadcastMessage(new UserLeave(
                        new UserData(ctx.getUsername())
                ));
            }
            messageService.removeUser(ctx);
            SessionManager.removeUser(ctx.getUsername());
        }
    }

    private boolean checkSessionToken(UserContext ctx, ClientMessage msg) throws IOException {
        if (msg.getType() == MessageType.AUTH_REQUEST) {
            if (ctx.isAuthorized()) {
                messageService.sendMessage(ctx, new AuthFail(new ErrorData("already authorized")));
                return false;
            } else {
                return true;
            }
        } else if (!ctx.isAuthorized()) {
            messageService.sendMessage(ctx, new AuthFail(new ErrorData("unauthorized")));
            return false;
        }

        if (msg.getSessionToken() == null || msg.getSessionToken().token() == null || ctx.getSessionToken() == null ||
                !Objects.equals(msg.getSessionToken().token(), ctx.getSessionToken())) {
            messageService.sendMessage(ctx, new AuthFail(new ErrorData("invalid session token")));
            return false;
        }

        return true;
    }
}
