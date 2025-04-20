package ru.shift;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.config.Config;
import ru.shift.dto.TaskInfo;
import ru.shift.exception.InvalidOptionException;

import java.util.Scanner;
import java.util.function.Function;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final Function<Long, Double> FUNC = input -> 1D / (input * (input + 1L));
    private static final long FUNC_LOW = 1L;
    private static final double EXPECTED_RESULT = 1D;

    public static void main(String[] args) {
        Config cfg = new Config();
        try {
            cfg.loadFromArgs(args);
        } catch (ParseException | InvalidOptionException e) {
            log.error("failed to parse args: {}", e.getMessage());
            log.warn("using default config");
            cfg.setDefaults();
        }

        Scanner scanner = new Scanner(System.in);
        long limit;
        try {
            System.out.print("Enter limit (N): ");
            limit = scanner.nextLong();
        } catch (RuntimeException e) {
            log.fatal("failed to read input: {}", e.getMessage());
            return;
        }

        try {
            MultiThreadComputer mtc = new MultiThreadComputer(
                    // limit + 1 because high limit is excluded
                    new TaskInfo(FUNC, FUNC_LOW, limit + 1, cfg.getSingleThreadThreshold()),
                    cfg.getMaxThreads()
            );
            double result = mtc.calculate();
            log.info("calculation finished with result: {}", String.format("%.17f", result));
            log.info("expected result: {}", EXPECTED_RESULT);
        } catch (RuntimeException e) {
            log.fatal("failed to calculate: {}", e.getMessage());
        }
    }
}
