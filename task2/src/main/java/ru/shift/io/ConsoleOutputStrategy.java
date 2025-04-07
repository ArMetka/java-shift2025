package ru.shift.io;

import java.io.IOException;
import java.io.PrintWriter;

public class ConsoleOutputStrategy implements OutputStrategy {
    @Override
    public PrintWriter newPrintWriter() throws IOException {
        return new PrintWriter(System.out);
    }

    @Override
    public void close() throws Exception {
    }
}
