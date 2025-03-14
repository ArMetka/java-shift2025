package ru.shift.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class TablePrinter {
    private final char columnDelimiter;
    private final char rowDelimiter;
    private final char crossChar;
    private final int tableSize;
    private final int firstColumnWidth;
    private final int defaultColumnWidth;

    public TablePrinter(char columnDelimiter,
                        char rowDelimiter,
                        char crossChar,
                        int tableSize) {
        this.columnDelimiter = columnDelimiter;
        this.rowDelimiter = rowDelimiter;
        this.crossChar = crossChar;
        this.tableSize = tableSize;
        firstColumnWidth = getNumberWidth(tableSize);
        defaultColumnWidth = getNumberWidth(tableSize * tableSize);
    }

    public void printTable() throws IOException {
        try (BufferedWriter out = new BufferedWriter(new PrintWriter(System.out))) {
            for (int row = 0; row < tableSize + 1; row++) {
                for (int col = 0; col < tableSize + 1; col++) {
                    if (col == 0) {
                        writeNumberSpaced(out, firstColumnWidth, row);
                    } else if (row == 0) {
                        writeNumberSpaced(out, defaultColumnWidth, col);
                    } else {
                        writeNumberSpaced(out, defaultColumnWidth, row * col);
                    }

                    if (col != tableSize) {
                        out.write(columnDelimiter);
                    }
                }
                out.newLine();
                writeRowDelimiter(out);
                out.newLine();
            }
            out.flush();
        }
    }

    private int getNumberWidth(int number) {
        if (number == 0) return 0;

        int width = 1;

        while (number > 9) {
            width += 1;
            number /= 10;
        }

        return width;
    }

    private void writeNumberSpaced(Writer out, int totalWidth, int number) throws IOException {
        for (int i = 0; i < totalWidth - getNumberWidth(number); i++) {
            out.write(" ");
        }

        if (number != 0) {
            out.write(String.valueOf(number));
        }
    }

    private void writeRowDelimiter(Writer out) throws IOException {
        for (int i = 0; i < firstColumnWidth; i++) {
            out.write(rowDelimiter);
        }

        for (int i = 0; i < tableSize; i++) {
            out.write(crossChar);
            for (int j = 0; j < defaultColumnWidth; j++) {
                out.write(rowDelimiter);
            }
        }
    }
}
