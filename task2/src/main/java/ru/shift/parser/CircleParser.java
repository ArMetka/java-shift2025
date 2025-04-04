package ru.shift.parser;

import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Circle;
import ru.shift.shape.Shape;

import java.util.Objects;

public class CircleParser implements ShapeParser {
    @Override
    public Shape create(ShapeData data) {
        if (data.getParams().length != 1) {
            throw new InvalidShapeParamsException("Expected 1 circle parameter, got " + data.getParams().length);
        }

        return new Circle(
                Double.parseDouble(data.getParams()[0])
        );
    }

    @Override
    public boolean supports(ShapeData data) {
        return Objects.equals(data.getCode(), Circle.CODE_STR);
    }

    @Override
    public String getCode() {
        return Circle.CODE_STR;
    }
}
