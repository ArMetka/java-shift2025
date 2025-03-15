package ru.shift;

import ru.shift.config.Config;
import ru.shift.io.InputHandler;
import ru.shift.io.TablePrinter;

import java.io.IOException;
import java.util.NoSuchElementException;

public class MultiplicationTableGenerator {
    private int tableSize;

    public void getInput() {
        InputHandler inputHandler = new InputHandler();
        try {
            tableSize = inputHandler.getSizeFromUser(Config.MIN_SIZE, Config.MAX_SIZE);
        } catch (NoSuchElementException | IllegalStateException ex) {
            System.err.println("Failed to read size");
            System.exit(1);
        }
    }

    public void printTable() {
        TablePrinter tablePrinter = new TablePrinter(
                Config.COL_DELIMITER,
                Config.ROW_DELIMITER,
                Config.CROSS_CHAR,
                tableSize
        );

        try {
            tablePrinter.printTable();
        } catch (IOException ex) {
            System.err.println("Failed to print table: " + ex.getMessage());
            System.exit(1);
        }
    }
}