package ru.shift.shape;

import ru.shift.dto.ShapeData;
import ru.shift.exception.UnknownShapeException;
import ru.shift.parser.ShapeParser;
import ru.shift.parser.ShapeParsersRegistry;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class ShapeReader {
    private static final String PARAMETER_DELIMITER = " ";

    public static Shape read(BufferedReader reader) throws IOException {
        ShapeData data = new ShapeData(
                reader.readLine(),
                reader.readLine().split(PARAMETER_DELIMITER)
        );

        ShapeParser parser = ShapeParsersRegistry.findByShapeData(data);

        if (parser == null) {
            throw new UnknownShapeException("Unknown shape: " + data.getCode());
        }

        return parser.create(data);
    }
}
