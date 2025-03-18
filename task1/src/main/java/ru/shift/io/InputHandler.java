package ru.shift.io;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    public int getSizeFromUser(int minSize, int maxSize) {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter multiplication table size: ");

                try {
                    int result = in.nextInt();

                    if (result < minSize || result > maxSize) {
                        throw new InputMismatchException();
                    }

                    return result;
                } catch (InputMismatchException ex) {
                    System.err.println("Invalid input! Expecting integer in range [" + minSize + ", " + maxSize + "]");
                    in.nextLine();
                }
            }
        }
    }
}
