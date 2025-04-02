package ru.shift.io;

import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Triangle;

import java.io.BufferedReader;
import java.io.IOException;

public class TriangleReader extends ShapeReader {
    public static Triangle read(BufferedReader reader) throws IOException {
        var params = readParams(reader);

        if (params.length != 3) {
            throw new InvalidShapeParamsException("Expected 3 triangle parameters, got " + params.length);
        }

        return new Triangle(
                Double.parseDouble(params[0]),
                Double.parseDouble(params[1]),
                Double.parseDouble(params[2])
        );
    }
}
