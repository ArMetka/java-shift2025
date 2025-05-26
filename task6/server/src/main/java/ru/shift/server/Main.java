package ru.shift.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.message.impl.DisconnectNotification;
import ru.shift.server.config.Config;
import ru.shift.server.service.BroadcastService;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Config cfg;
        try {
            cfg = Config.fromArgs(args);
        } catch (Exception e) {
            log.fatal("failed to parse args", e);
            return;
        }

        var broadcastService = new BroadcastService();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            broadcastService.broadcast(new DisconnectNotification());
        }));
        try {
            new ChatServer(cfg.getServerConfig(), broadcastService).run();
        } catch (Exception e) {
            log.fatal("failed to initialize server", e);
        }
    }
}