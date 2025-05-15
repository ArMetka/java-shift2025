package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.config.Config;

import java.io.IOException;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Config cfg;
        try {
            cfg = new Config(args);
            cfg.loadFromProperties();
        } catch (IOException e) {
            log.fatal("IOException while reading properties: {}", e.getMessage());
            return;
        } catch (NumberFormatException e) {
            log.fatal("Failed to parse property value: {}", e.getMessage());
            return;
        } catch (Exception e) {
            log.fatal("Failed to parse properties: {}", e.getMessage());
            return;
        }

        var mtp = new MultiThreadProduction(cfg.getProductionData());
        mtp.start();
    }
}
