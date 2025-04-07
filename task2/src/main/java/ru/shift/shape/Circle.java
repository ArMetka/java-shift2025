package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;

public class Circle extends Shape {
    public static final String CODE_STR = "CIRCLE";
    private static final String NAME_STR = "Circle";
    private static final String CIRCLE_RADIUS_OUT_STR = "Radius: ";
    private static final String CIRCLE_DIAMETER_OUT_STR = "Diameter: ";

    private final double radius;
    private final double diameter;

    public Circle(double radius) {
        validateRadius(radius);

        this.radius = radius;

        diameter = radius * 2;
        setArea(Math.PI * radius * radius);
        setPerimeter(Math.PI * diameter);
    }

    private void validateRadius(double radius) {
        if (radius <= 0) {
            throw new InvalidShapeParamsException("Circle radius must be > 0");
        }
    }

    @Override
    public void write(PrintWriter writer) {
        writeCommon(writer, NAME_STR);

        writer.write(CIRCLE_RADIUS_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, radius);
        writer.write(System.lineSeparator());

        writer.write(CIRCLE_DIAMETER_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, diameter);
        writer.write(System.lineSeparator());

        writer.flush();
    }
}
