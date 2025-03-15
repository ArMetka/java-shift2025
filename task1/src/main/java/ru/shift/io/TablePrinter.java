package ru.shift.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TablePrinter {
    private final int tableSize;

    private final char columnDelimiter;
    private final char rowDelimiter;
    private final char crossChar;

    private final int firstColumnWidth;
    private final int defaultColumnWidth;
    private final String rowDelimiterStr;

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
        rowDelimiterStr = getRowDelimiterStr();
    }

    public void printTable() throws IOException {
        printTable(System.out);
    }

    public void printTable(OutputStream outputStream) throws IOException {
        try (PrintWriter out = new PrintWriter(outputStream)) {

            out.write(" ".repeat(firstColumnWidth));
            printRow(out, 1);

            for (int row = 1; row < tableSize + 1; row++) {
                out.printf("%" + firstColumnWidth + "d", row);
                printRow(out, row);
            }

            out.flush();
        }
    }

    private void printRow(PrintWriter out, int row) {
        for (int col = 1; col < tableSize + 1; col++) {
            out.write(columnDelimiter);
            out.printf("%" + defaultColumnWidth + "d", row * col);
        }
        out.println();
        out.println(rowDelimiterStr);
    }

    private int getNumberWidth(int number) {
        if (number == 0) {
            return 0;
        }

        int width = 1;

        while (number > 9) {
            width += 1;
            number /= 10;
        }

        return width;
    }

    private String getRowDelimiterStr() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.valueOf(rowDelimiter).repeat(firstColumnWidth));

        for (int i = 0; i < tableSize; i++) {
            sb.append(crossChar);
            sb.append(String.valueOf(rowDelimiter).repeat(defaultColumnWidth));
        }

        return sb.toString();
    }
}
