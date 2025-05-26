package ru.shift.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.connection.ChatConnection;
import ru.shift.common.message.impl.JsonMessageMapper;
import ru.shift.server.context.UserContext;
import ru.shift.server.controller.ControllerRegistry;
import ru.shift.server.dto.ServerConfig;
import ru.shift.server.exception.InvalidServerConfigException;
import ru.shift.server.service.BroadcastService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Runnable {
    private static final Logger log = LogManager.getLogger(ChatServer.class);
    private final BroadcastService broadcastService;
    private final int port;
    private final int maxThreads;

    public ChatServer(ServerConfig cfg, BroadcastService broadcastService) {
        validateServerConfig(cfg);
        port = cfg.port();
        maxThreads = cfg.maxThreads();
        this.broadcastService = broadcastService;
    }

    private void validateServerConfig(ServerConfig cfg) {
        if (cfg.port() < ServerConfig.PORT_MIN_VALUE || cfg.port() > ServerConfig.PORT_MAX_VALUE) {
            throw new InvalidServerConfigException(
                    "port must be in range " +
                            "[" + ServerConfig.PORT_MIN_VALUE + "; " + ServerConfig.PORT_MAX_VALUE + "]" +
                            ", got: " + cfg.port()
            );
        }

        if (cfg.maxThreads() < ServerConfig.THREADS_MIN_VALUE) {
            throw new InvalidServerConfigException(
                    "number of threads must be >= " + ServerConfig.THREADS_MIN_VALUE +
                            ", got: " + cfg.maxThreads()
            );
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
                pool.submit(() -> handleConnection(client));
            }
        } catch (Exception e) {
            log.fatal("failed to start server", e);
        }
        pool.shutdownNow();
    }

    private void handleConnection(Socket socket) {
        try (var ctx = new UserContext(new ChatConnection(socket, new JsonMessageMapper()), broadcastService)) {
            ctx.getConnection().readMessagesLoop((msg) -> {
                var controller = ControllerRegistry.findByMessageType(msg.getType());
                if (controller != null) {
                    controller.process(ctx, msg);
                } else {
                    log.warn("received unsupported message type {}", msg.getType().name());
                }
            });
            log.info("client disconnected: {}", ctx.getUsername());
        } catch (Exception e) {
            log.error("connection terminated", e);
        }
    }
}
