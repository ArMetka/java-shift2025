package ru.shift.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePropertiesInputStrategy implements PropertiesInputStrategy {
    private final String filename;
    private InputStream in;

    public FilePropertiesInputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        in = Files.newInputStream(Path.of(filename));
        return in;
    }

    @Override
    public void close() throws Exception {
        if (in != null) {
            in.close();
        }
    }
}
