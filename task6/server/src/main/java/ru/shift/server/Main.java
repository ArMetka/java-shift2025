package ru.shift.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.server.config.Config;
import ru.shift.server.service.IMessageService;
import ru.shift.server.service.MessageService;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Config cfg;
        try {
            cfg = Config.fromArgs(args);
        } catch (Exception e) {
            log.fatal("failed to parse args: {}", e.getMessage());
            return;
        }

        IMessageService messageService = new MessageService();
        try {
            new ChatServer(cfg.getServerConfig(), messageService).run();
        } catch (Exception e) {
            log.fatal("failed to initialize server: {}", e.getMessage());
        }
    }
}