package ru.shift;

public class Main {
    public static void main(String[] args) {
        MultiplicationTableGenerator mtg = new MultiplicationTableGenerator();
        int tableSize = mtg.getInput();
        mtg.printTable(tableSize);
    }
}
