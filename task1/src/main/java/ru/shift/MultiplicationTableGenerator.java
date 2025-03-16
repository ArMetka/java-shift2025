package ru.shift;

import ru.shift.config.Config;
import ru.shift.io.InputHandler;
import ru.shift.io.TablePrinter;

import java.io.IOException;
import java.util.NoSuchElementException;

public class MultiplicationTableGenerator {
    public int getInput() {
        InputHandler inputHandler = new InputHandler();
        try {
            return inputHandler.getSizeFromUser(Config.MIN_SIZE, Config.MAX_SIZE);
        } catch (NoSuchElementException | IllegalStateException ex) {
            System.err.println("Failed to read size");
            return 0;
        }
    }

    public void printTable(int tableSize) {
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
            tablePrinter.printTable(System.out);
        } catch (IOException ex) {
            System.err.println("Failed to print table: " + ex.getMessage());
            System.exit(1);
        }
    }
}