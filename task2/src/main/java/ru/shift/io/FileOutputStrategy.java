package ru.shift.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOutputStrategy implements OutputStrategy {
    private final String filename;
    private PrintWriter printWriter;

    public FileOutputStrategy(String filename) {
        this.filename = filename;
    }

    @Override
    public PrintWriter newPrintWriter() throws IOException {
        printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get(filename)));

        return printWriter;
    }

    @Override
    public void close() throws Exception {
        printWriter.close();
    }
}
