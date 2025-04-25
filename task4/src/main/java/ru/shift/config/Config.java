package ru.shift.config;

import org.apache.commons.cli.*;
import ru.shift.exception.InvalidOptionException;

public class Config {
    private static final String THREAD_NUM_OPT = "t";
    private static final String THREAD_NUM_LONG_OPT = "threads";
    private static final String SINGLE_THREAD_THRESHOLD_OPT = "l";
    private static final String SINGLE_THREAD_THRESHOLD_LONG_OPT = "threshold";

    private final Options options = new Options();

    private int maxThreads;
    private long singleThreadThreshold;

    public static Config fromArgs(String[] args) throws ParseException {
        Config cfg = new Config();

        cfg.setupOptions();
        cfg.readOptions(args);

        return cfg;
    }

    private Config() {
    }

    private void setupOptions() {
        options.addOption(
                THREAD_NUM_OPT,
                THREAD_NUM_LONG_OPT,
                true,
                "set maximum number of threads"
        );

        options.addOption(
                SINGLE_THREAD_THRESHOLD_OPT,
                SINGLE_THREAD_THRESHOLD_LONG_OPT,
                true,
                "set work threshold for single thread execution"
        );
    }

    private void readOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser(false);
        CommandLine cmd = parser.parse(options, args);

        var maxThreadsInput = cmd.getOptionValue(THREAD_NUM_OPT);
        if (maxThreadsInput != null) {
            var input = Integer.parseInt(maxThreadsInput);
            if (input < 1) {
                throw new InvalidOptionException("thread num must be > 0");
            }
            maxThreads = input;
        } else {
            maxThreads = Runtime.getRuntime().availableProcessors();
        }

        var singleThreadThresholdInput = cmd.getOptionValue(SINGLE_THREAD_THRESHOLD_OPT);
        if (singleThreadThresholdInput != null) {
            var input = Long.parseLong(singleThreadThresholdInput);
            if (input < 2) {
                throw new InvalidOptionException("threshold must be > 1");
            }
            singleThreadThreshold = input;
        } else {
            singleThreadThreshold = 1_000_000;
        }
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public long getSingleThreadThreshold() {
        return singleThreadThreshold;
    }
}
