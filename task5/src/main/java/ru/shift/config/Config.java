package ru.shift.config;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.dto.ProductionData;
import ru.shift.exception.InvalidPropertiesException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_LOCATION_OPT = "p";
    private static final String PROPERTIES_LOCATION_OPT_LONG = "properties";
    private static final String PRODUCER_COUNT_PROPERTY = "producerCount";
    private static final String CONSUMER_COUNT_PROPERTY = "consumerCount";
    private static final String PRODUCER_TIME_PROPERTY = "producerTime";
    private static final String CONSUMER_TIME_PROPERTY = "consumerTime";
    private static final String STORAGE_SIZE_PROPERTY = "storageSize";
    private static final String DEFAULT_PROPERTIES_FILENAME = "default.properties";

    private final Options options = new Options();

    private String propertiesPath = null;
    private PropertiesInputStrategy inputStrategy;

    private ProductionData productionData;

    public Config(String[] args) throws ParseException {
        setupOptions();
        loadPropertiesFileLocation(args);
    }

    public void loadFromProperties() throws IOException {
        if (propertiesPath == null) {
            inputStrategy = new ResourcePropertiesInputStrategy(DEFAULT_PROPERTIES_FILENAME);
        } else {
            inputStrategy = new FilePropertiesInputStrategy(propertiesPath);
        }

        try (var in = inputStrategy.getInputStream()) {
            var properties = new Properties();
            properties.load(in);

            productionData = new ProductionData(
                    Integer.parseInt(properties.getProperty(PRODUCER_COUNT_PROPERTY)),
                    Integer.parseInt(properties.getProperty(CONSUMER_COUNT_PROPERTY)),
                    Integer.parseInt(properties.getProperty(PRODUCER_TIME_PROPERTY)),
                    Integer.parseInt(properties.getProperty(CONSUMER_TIME_PROPERTY)),
                    Integer.parseInt(properties.getProperty(STORAGE_SIZE_PROPERTY))
            );
        }
        validateProductionData();
    }

    private void validateProductionData() {
        if (productionData.producerCount() < 1) {
            throw new InvalidPropertiesException("producerCount must be greater than 0");
        }
        if (productionData.consumerCount() < 1) {
            throw new InvalidPropertiesException("consumerCount must be greater than 0");
        }

        if (productionData.storageSize() < 1) {
            throw new InvalidPropertiesException("storageSize must be greater than 0");
        }

        if (productionData.producerTime() <= 0) {
            throw new InvalidPropertiesException("producerTime must be positive");
        }
        if (productionData.consumerTime() <= 0) {
            throw new InvalidPropertiesException("consumerTime must be positive");
        }
    }

    private void setupOptions() {
        options.addOption(
                PROPERTIES_LOCATION_OPT,
                PROPERTIES_LOCATION_OPT_LONG,
                true,
                "specify the location of the .properties file"
        );
    }

    private void loadPropertiesFileLocation(String[] args) throws ParseException {
        var parser = new DefaultParser(false);

        var cmd = parser.parse(options, args);
        propertiesPath = cmd.getOptionValue(PROPERTIES_LOCATION_OPT);
        if (propertiesPath != null) {
            validatePropertiesPath();
        }
    }

    private void validatePropertiesPath() {
        if (!new File(propertiesPath).isFile()) {
            throw new InvalidPropertiesException("invalid file: " + propertiesPath);
        }
    }

    public ProductionData getProductionData() {
        return productionData;
    }
}
