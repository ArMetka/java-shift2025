package ru.shift.parser;

import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Rectangle;
import ru.shift.shape.Shape;

import java.util.Objects;

public class RectangleParser implements ShapeParser {
    @Override
    public Shape create(ShapeData data) {
        if (data.getParams().length != 2) {
            throw new InvalidShapeParamsException("Expected 2 rectangle parameters, got " + data.getParams().length);
        }

        return new Rectangle(
                Double.parseDouble(data.getParams()[0]),
                Double.parseDouble(data.getParams()[1])
        );
    }

    @Override
    public boolean supports(ShapeData data) {
        return Objects.equals(data.getCode(), Rectangle.CODE_STR);
    }

    @Override
    public String getCode() {
        return Rectangle.CODE_STR;
    }
}
