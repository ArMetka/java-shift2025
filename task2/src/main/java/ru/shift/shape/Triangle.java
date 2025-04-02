package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;

public class Triangle extends Shape {
    public static final String CODE_STR = "TRIANGLE";
    private static final String NAME_STR = "Triangle";
    private static final String TRIANGLE_LEN_OF_SIDE1_OUT_STR = "Length of side 1: ";
    private static final String TRIANGLE_LEN_OF_SIDE2_OUT_STR = "Length of side 2: ";
    private static final String TRIANGLE_LEN_OF_SIDE3_OUT_STR = "Length of side 3: ";
    private static final String TRIANGLE_OPPOSITE_ANGLE1_OUT_STR = "Opposite angle of side 1: ";
    private static final String TRIANGLE_OPPOSITE_ANGLE2_OUT_STR = "Opposite angle of side 2: ";
    private static final String TRIANGLE_OPPOSITE_ANGLE3_OUT_STR = "Opposite angle of side 3: ";

    private final double side1;
    private final double side2;
    private final double side3;
    private final double angle1;
    private final double angle2;
    private final double angle3;

    public Triangle(double side1, double side2, double side3) {
        validateSide(side1);
        validateSide(side2);
        validateSide(side3);

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;

        validateTriangle();

        setPerimeter(side1 + side2 + side3);
        setArea(Math.sqrt(getPerimeter() * (side2 + side3 - side1) * (side1 - side2 + side3) * (side1 + side2 - side3)) / 4);
        angle1 = Math.acos((side2 * side2 + side3 * side3 - side1 * side1) / (2 * side2 * side3));
        angle2 = Math.acos((side1 * side1 + side3 * side3 - side2 * side2) / (2 * side1 * side3));
        angle3 = Math.acos((side1 * side1 + side2 * side2 - side3 * side3) / (2 * side1 * side2));
    }

    private void validateSide(double triangleSide) {
        if (triangleSide <= 0) {
            throw new InvalidShapeParamsException("Triangle side must be > 0");
        }
    }

    private void validateTriangle() {
        if (side1 + side2 <= side3 || side1 + side3 <= side2 || side2 + side3 <= side1) {
            throw new InvalidShapeParamsException("Any 2 triangle sides must be greater than 3rd");
        }
    }

    @Override
    public void write(PrintWriter writer) {
        writeCommon(writer, NAME_STR);

        writer.write(TRIANGLE_LEN_OF_SIDE1_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, side1);
        writer.write(System.lineSeparator());
        writer.write(TRIANGLE_OPPOSITE_ANGLE1_OUT_STR);
        writer.printf(RAD_UNITS_OUTPUT_FORMAT, angle1);
        writer.write(System.lineSeparator());

        writer.write(TRIANGLE_LEN_OF_SIDE2_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, side2);
        writer.write(System.lineSeparator());
        writer.write(TRIANGLE_OPPOSITE_ANGLE2_OUT_STR);
        writer.printf(RAD_UNITS_OUTPUT_FORMAT, angle2);
        writer.write(System.lineSeparator());

        writer.write(TRIANGLE_LEN_OF_SIDE3_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, side3);
        writer.write(System.lineSeparator());
        writer.write(TRIANGLE_OPPOSITE_ANGLE3_OUT_STR);
        writer.printf(RAD_UNITS_OUTPUT_FORMAT, angle3);
        writer.write(System.lineSeparator());

        writer.flush();
    }
}
