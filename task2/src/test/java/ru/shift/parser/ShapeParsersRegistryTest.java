package ru.shift.parser;

import org.junit.jupiter.api.Test;
import ru.shift.dto.ShapeData;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShapeParsersRegistryTest {
    @Test
    public void findByShapeData_codeNull_returnNull() {
        ShapeData data = new ShapeData(null, null);

        var result = ShapeParsersRegistry.findByShapeData(data);

        assertNull(result);
    }

    @Test
    public void findByShapeData_codeUnknown_returnNull() {
        ShapeData data = new ShapeData("non-existent", null);

        var result = ShapeParsersRegistry.findByShapeData(data);

        assertNull(result);
    }

    @Test
    public void findByShapeData_codeCircle_returnCircleParser() {
        ShapeData data = new ShapeData("CIRCLE", null);

        var result = ShapeParsersRegistry.findByShapeData(data);

        assertInstanceOf(CircleParser.class, result);
    }

    @Test
    public void findByShapeData_codeRectangle_returnRectangleParser() {
        ShapeData data = new ShapeData("RECTANGLE", null);

        var result = ShapeParsersRegistry.findByShapeData(data);

        assertInstanceOf(RectangleParser.class, result);
    }

    @Test
    public void findByShapeData_codeTriangle_returnTriangleParser() {
        ShapeData data = new ShapeData("TRIANGLE", null);

        var result = ShapeParsersRegistry.findByShapeData(data);

        assertInstanceOf(TriangleParser.class, result);
    }
}