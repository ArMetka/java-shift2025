package ru.shift;

import ru.shift.exception.PrintTableException;
import ru.shift.exception.ReadSizeException;

public class Main {
    public static void main(String[] args) {
        MultiplicationTableGenerator mtg = new MultiplicationTableGenerator();
        try {
            int tableSize = mtg.getInput();
            mtg.printTable(tableSize, System.out);
        } catch (ReadSizeException | PrintTableException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
