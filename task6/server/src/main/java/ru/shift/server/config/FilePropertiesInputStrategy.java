package ru.shift.server.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;
    private InputStream in = null;

    public FilePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (in != null) {
            throw new IllegalStateException("input stream already opened");
        }

        in = new FileInputStream(filename);
        return in;
    }

    @Override
    public void close() throws Exception {
        if (in != null) {
            in.close();
        }
    }
}
