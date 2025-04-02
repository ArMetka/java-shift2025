package ru.shift.io;

import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Circle;

import java.io.BufferedReader;
import java.io.IOException;

public class CircleReader extends ShapeReader {
    public static Circle read(BufferedReader reader) throws IOException {
        var params = readParams(reader);

        if (params.length != 1) {
            throw new InvalidShapeParamsException("Expected 1 circle parameter, got " + params.length);
        }

        return new Circle(
                Double.parseDouble(params[0])
        );
    }
}
