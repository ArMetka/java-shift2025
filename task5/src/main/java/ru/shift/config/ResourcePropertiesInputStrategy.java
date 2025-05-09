package ru.shift.config;

import java.io.InputStream;

public class ResourcePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;
    private InputStream in;

    public ResourcePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() {
        in = getClass().getClassLoader().getResourceAsStream(filename);
        return in;
    }

    @Override
    public void close() throws Exception {
        if (in != null) {
            in.close();
        }
    }
}
