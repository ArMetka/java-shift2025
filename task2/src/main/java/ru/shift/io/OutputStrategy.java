package ru.shift.io;

import java.io.IOException;
import java.io.PrintWriter;

public interface OutputStrategy extends AutoCloseable {
    PrintWriter newPrintWriter() throws IOException;
}
