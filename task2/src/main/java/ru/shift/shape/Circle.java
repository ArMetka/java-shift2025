package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Circle extends Shape {
    public static final String CODE_STR = "CIRCLE";
    private static final String NAME_STR = "Circle";
    private static final String CIRCLE_RADIUS_OUT_STR = "Radius: ";
    private static final String CIRCLE_DIAMETER_OUT_STR = "Diameter: ";

    private final double radius;
    private double diameter;

    public Circle(double radius) throws InvalidShapeParamsException {
        validateRadius(radius);

        this.radius = radius;

        calculate();
    }

    private void validateRadius(double radius) throws InvalidShapeParamsException {
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

    private void calculate() {
        area = Math.PI * radius * radius;
        diameter = radius * 2;
        perimeter = Math.PI * diameter;
    }

    public static Circle read(BufferedReader reader) throws IOException, InvalidShapeParamsException {
        String[] params = reader.readLine().split(PARAMETER_DELIMITER);

        if (params.length != 1) {
            throw new InvalidShapeParamsException("Expected 1 circle parameter, got " + params.length);
        }

        try {
            return new Circle(
                    Double.parseDouble(params[0])
            );
        } catch (NumberFormatException ex) {
            throw new InvalidShapeParamsException("Failed to parse double: " + ex.getMessage());
        }
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return diameter;
    }
}
