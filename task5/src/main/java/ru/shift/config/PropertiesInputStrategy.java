package ru.shift.config;

import java.io.IOException;
import java.io.InputStream;

public interface PropertiesInputStrategy extends AutoCloseable {
    InputStream getInputStream() throws IOException;
}
