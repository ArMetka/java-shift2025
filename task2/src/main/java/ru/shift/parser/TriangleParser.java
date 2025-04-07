package ru.shift.parser;

import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Shape;
import ru.shift.shape.Triangle;

import java.util.Objects;

public class TriangleParser implements ShapeParser {
    @Override
    public Shape create(ShapeData data) {
        if (data.getParams().length != 3) {
            throw new InvalidShapeParamsException("Expected 3 triangle parameters, got " + data.getParams().length);
        }

        return new Triangle(
                Double.parseDouble(data.getParams()[0]),
                Double.parseDouble(data.getParams()[1]),
                Double.parseDouble(data.getParams()[2])
        );
    }

    @Override
    public boolean supports(ShapeData data) {
        return Objects.equals(data.getCode(), Triangle.CODE_STR);
    }

    @Override
    public String getCode() {
        return Triangle.CODE_STR;
    }
}
