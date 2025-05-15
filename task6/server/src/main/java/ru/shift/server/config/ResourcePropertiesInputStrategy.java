package ru.shift.server.config;

import java.io.InputStream;

public class ResourcePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;
    private InputStream in = null;

    public ResourcePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() {
        if (in != null) {
            throw new IllegalStateException("input stream already opened");
        }

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
