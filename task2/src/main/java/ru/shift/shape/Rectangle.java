package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Rectangle extends Shape {
    public static final String CODE_STR = "RECTANGLE";
    private static final String NAME_STR = "Rectangle";
    private static final String RECTANGLE_LEN_OF_DIAGONAL_OUT_STR = "Length of diagonal: ";
    private static final String RECTANGLE_LEN_OUT_STR = "Length: ";
    private static final String RECTANGLE_WIDTH_OUT_STR = "Width: ";

    private final double length;
    private final double width;
    private double lenOfDiagonal;

    public Rectangle(double side1, double side2) throws InvalidShapeParamsException {
        validateSide(side1);
        validateSide(side2);

        if (side1 > side2) {
            length = side1;
            width = side2;
        } else {
            length = side2;
            width = side1;
        }

        calculate();
    }

    private void validateSide(double rectangleSide) throws InvalidShapeParamsException {
        if (rectangleSide <= 0) {
            throw new InvalidShapeParamsException("Rectangle side must be > 0");
        }
    }

    @Override
    public void write(PrintWriter writer) {
        writeCommon(writer, NAME_STR);

        writer.write(RECTANGLE_LEN_OF_DIAGONAL_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, lenOfDiagonal);
        writer.write(System.lineSeparator());

        writer.write(RECTANGLE_LEN_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, length);
        writer.write(System.lineSeparator());

        writer.write(RECTANGLE_WIDTH_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, width);
        writer.write(System.lineSeparator());

        writer.flush();
    }

    private void calculate() {
        area = length * width;
        perimeter = length * 2 + perimeter * 2;
        lenOfDiagonal = Math.sqrt(length * length + width * width);
    }

    public static Rectangle read(BufferedReader reader) throws IOException, InvalidShapeParamsException {
        String[] params = reader.readLine().split(PARAMETER_DELIMITER);

        if (params.length != 2) {
            throw new InvalidShapeParamsException("Expected 2 rectangle parameters, got " + params.length);
        }

        try {
            return new Rectangle(
                    Double.parseDouble(params[0]),
                    Double.parseDouble(params[1])
            );
        } catch (NumberFormatException ex) {
            throw new InvalidShapeParamsException("Failed to parse double: " + ex.getMessage());
        }
    }
}
