package ru.shift;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.config.Config;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Config cfg = new Config();
        try {
            cfg.loadFromArgs(args);
        } catch (ParseException ex) {
            log.fatal(ex.getMessage());
            log.info("Usage: [-o output_file] input_file...");
            return;
        }

        ShapesCalculator sc = new ShapesCalculator(
                cfg.getInputFiles(),
                cfg.getOutputFile()
        );
        try {
            sc.calculate();
        } catch (Exception ex) {
            log.fatal(ex.getMessage());
        }
    }
}