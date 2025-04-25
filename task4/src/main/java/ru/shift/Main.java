package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.config.Config;
import ru.shift.dto.TaskInfo;

import java.util.Scanner;
import java.util.function.Function;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    private static final Function<Long, Double> FUNC = input -> 1D / (input * (input + 1L));
    private static final long FUNC_LOW = 1L;
    private static final double EXPECTED_RESULT = 1D;

    public static void main(String[] args) {
        Config cfg;
        try {
            cfg = Config.fromArgs(args);
        } catch (Exception e) {
            log.fatal("failed to parse args: {}", e.getMessage());
            return;
        }

        long limit;
        try {
            var scanner = new Scanner(System.in);
            System.out.print("Enter limit (N): ");
            limit = scanner.nextLong();
        } catch (Exception e) {
            log.fatal("failed to read input: {}", e.getMessage());
            return;
        }

        try {
            var result = new MultiThreadComputer(
                    new TaskInfo(FUNC, FUNC_LOW, limit),
                    cfg.getMaxThreads(),
                    cfg.getSingleThreadThreshold()
            ).calculate();

            var resultStr = String.format("%.17f", result);

            log.info("calculation finished with result: {}, expected: {}", resultStr, EXPECTED_RESULT);
            System.out.println("result = " + resultStr);
            System.out.println("expected = " + EXPECTED_RESULT);
        } catch (Exception e) {
            log.fatal("failed to calculate: {}", e.getMessage());
        }
    }
}
