package ru.shift.io;

import ru.shift.exception.UnknownShapeException;
import ru.shift.shape.Circle;
import ru.shift.shape.Rectangle;
import ru.shift.shape.Shape;
import ru.shift.shape.Triangle;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class ShapeReader {
    private static final String PARAMETER_DELIMITER = " ";

    public static Shape read(BufferedReader reader) throws IOException {
        String type = reader.readLine();
        return switch (type) {
            case (Circle.CODE_STR) -> CircleReader.read(reader);
            case (Rectangle.CODE_STR) -> RectangleReader.read(reader);
            case (Triangle.CODE_STR) -> TriangleReader.read(reader);
            default -> throw new UnknownShapeException("Unknown shape: " + type);
        };
    }

    static String[] readParams(BufferedReader reader) throws IOException {
        return reader.readLine().split(PARAMETER_DELIMITER);
    }
}
