package ru.shift.shape;

import java.io.PrintWriter;

public abstract class Shape {
    protected static final String PARAMETER_DELIMITER = " ";
    protected static final String SHAPE_TYPE_OUT_STR = "Shape type: ";
    protected static final String SHAPE_AREA_OUT_STR = "Area: ";
    protected static final String SHAPE_PERIMETER_OUT_STR = "Perimeter: ";
    protected static final String MM_UNITS_OUTPUT_FORMAT = "%.2f mm";
    protected static final String MM_SQUARED_UNITS_OUTPUT_FORMAT = "%.2f mm^2";
    protected static final String RAD_UNITS_OUTPUT_FORMAT = "%.2f rad";

    protected double area;
    protected double perimeter;

    protected void writeCommon(PrintWriter writer, String name) {
        writer.write(SHAPE_TYPE_OUT_STR);
        writer.write(name);
        writer.write(System.lineSeparator());

        writer.write(SHAPE_AREA_OUT_STR);
        writer.printf(MM_SQUARED_UNITS_OUTPUT_FORMAT, area);
        writer.write(System.lineSeparator());

        writer.write(SHAPE_PERIMETER_OUT_STR);
        writer.printf(MM_UNITS_OUTPUT_FORMAT, perimeter);
        writer.write(System.lineSeparator());
    }

    public abstract void write(PrintWriter writer);

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
