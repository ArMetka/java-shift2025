package ru.shift.server.config;

import lombok.Getter;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import ru.shift.server.config.exception.InvalidPropertiesException;
import ru.shift.server.dto.ServerConfig;
import ru.shift.server.exception.InvalidServerConfigException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_LOCATION_OPT = "p";
    private static final String PROPERTIES_LOCATION_OPT_LONG = "properties";
    private static final String PORT_PROPERTY = "port";
    private static final String THREAD_NUM_PROPERTY = "threads";
    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "default.properties";

    private final Options options = new Options();
    private PropertiesInputStrategy inputStrategy;

    @Getter
    private ServerConfig serverConfig;

    public static Config fromArgs(String[] args) throws ParseException, IOException {
        var cfg = new Config();

        cfg.setupOptions();
        cfg.readOptions(args);
        cfg.readResource();

        return cfg;
    }

    private Config() {
    }

    private void setupOptions() {
        options.addOption(
                PROPERTIES_LOCATION_OPT,
                PROPERTIES_LOCATION_OPT_LONG,
                true,
                "specify the location of the .properties file"
        );
    }

    private void readOptions(String[] args) throws ParseException {
        var parser = new DefaultParser(false);
        var cmd = parser.parse(options, args);

        var propertiesPath = cmd.getOptionValue(PROPERTIES_LOCATION_OPT);
        if (propertiesPath != null) {
            validateFile(propertiesPath);
            inputStrategy = new FilePropertiesInputStrategy(propertiesPath);
        } else {
            inputStrategy = new ResourcePropertiesInputStrategy(DEFAULT_PROPERTIES_RESOURCE_NAME);
        }
    }

    private void readResource() throws IOException {
        try (var in = inputStrategy.getInputStream()) {
            var props = new Properties();
            props.load(in);

            serverConfig = new ServerConfig(
                    validatePort(Integer.parseInt(props.getProperty(PORT_PROPERTY))),
                    validateMaxThreads(Integer.parseInt(props.getProperty(THREAD_NUM_PROPERTY)))
            );
        }
    }

    private void validateFile(String file) {
        if (!new File(file).isFile()) {
            throw new InvalidPropertiesException("invalid file: " + file);
        }
    }

    private int validatePort(int port) {
        if (port < ServerConfig.PORT_MIN_VALUE || port > ServerConfig.PORT_MAX_VALUE) {
            throw new InvalidPropertiesException(
                    "port must be in range " +
                            "[" + ServerConfig.PORT_MIN_VALUE + "; " + ServerConfig.PORT_MAX_VALUE + "]" +
                            ", got: " + port
            );
        }

        return port;
    }

    private int validateMaxThreads(int maxThreads) {
        if (maxThreads < ServerConfig.THREADS_MIN_VALUE) {
            throw new InvalidPropertiesException(
                    "number of threads must be >= " + ServerConfig.THREADS_MIN_VALUE +
                            ", got: " + maxThreads
            );
        }

        return maxThreads;
    }
}
