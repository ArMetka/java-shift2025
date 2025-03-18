package ru.shift;

import ru.shift.config.Config;
import ru.shift.exception.PrintTableException;
import ru.shift.exception.ReadSizeException;
import ru.shift.io.InputHandler;
import ru.shift.io.TablePrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

public class MultiplicationTableGenerator {
    public int getInput() throws ReadSizeException {
        InputHandler inputHandler = new InputHandler();
        try {
            return inputHandler.getSizeFromUser(Config.MIN_SIZE, Config.MAX_SIZE);
        } catch (NoSuchElementException | IllegalStateException ex) {
            throw new ReadSizeException("Failed to read size", ex);
        }
    }

    public void printTable(int tableSize) throws PrintTableException {
        printTable(tableSize, System.out);
    }

    public void printTable(int tableSize, OutputStream outputStream) throws PrintTableException {
        if (tableSize == 0) {
            return;
        }

        TablePrinter tablePrinter = new TablePrinter(
                Config.COL_DELIMITER,
                Config.ROW_DELIMITER,
                Config.CROSS_CHAR,
                tableSize
        );

        try {
            tablePrinter.printTable(outputStream);
        } catch (IOException ex) {
            throw new PrintTableException("Failed to print table", ex);
        }
    }
}