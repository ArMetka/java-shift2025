package ru.shift.server.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;

    public FilePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(filename);
    }
}
