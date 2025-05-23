package ru.shift.server.config;

import java.io.InputStream;

public class ResourcePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;

    public ResourcePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }
}
