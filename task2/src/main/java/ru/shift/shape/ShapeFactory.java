package ru.shift.shape;

import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.exception.UnknownShapeException;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class ShapeFactory {
    public static Shape readShape(BufferedReader reader) throws IOException, UnknownShapeException, InvalidShapeParamsException {
        String type = reader.readLine();
        return switch (type) {
            case (Circle.CODE_STR) -> Circle.read(reader);
            case (Rectangle.CODE_STR) -> Rectangle.read(reader);
            case (Triangle.CODE_STR) -> Triangle.read(reader);
            default -> throw new UnknownShapeException("Unknown shape: " + type);
        };
    }
}
