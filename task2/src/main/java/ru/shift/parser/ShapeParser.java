package ru.shift.parser;

import ru.shift.dto.ShapeData;
import ru.shift.shape.Shape;

public interface ShapeParser {
    Shape create(ShapeData data);

    boolean supports(ShapeData data);

    String getCode();
}
