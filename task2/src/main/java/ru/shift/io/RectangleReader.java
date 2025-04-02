package ru.shift.io;

import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;

public class RectangleReader extends ShapeReader {
    public static Rectangle read(BufferedReader reader) throws IOException {
        var params = readParams(reader);

        if (params.length != 2) {
            throw new InvalidShapeParamsException("Expected 2 rectangle parameters, got " + params.length);
        }

        return new Rectangle(
                Double.parseDouble(params[0]),
                Double.parseDouble(params[1])
        );
    }
}
