package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;

public class Rectangle extends Shape {
    public static final String CODE_STR = "RECTANGLE";
    private static final String NAME_STR = "Rectangle";
    private static final String RECTANGLE_LEN_OF_DIAGONAL_OUT_STR = "Length of diagonal: ";
    private static final String RECTANGLE_LEN_OUT_STR = "Length: ";
    private static final String RECTANGLE_WIDTH_OUT_STR = "Width: ";

    private final double length;
    private final double width;
    private final double lenOfDiagonal;

    public Rectangle(double side1, double side2) {
        validateSide(side1);
        validateSide(side2);

        if (side1 > side2) {
            length = side1;
            width = side2;
        } else {
            length = side2;
            width = side1;
        }

        lenOfDiagonal = Math.hypot(length, width);
        setArea(length * width);
        setPerimeter(length * 2 + width * 2);
    }

    private void validateSide(double rectangleSide) {
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
}
