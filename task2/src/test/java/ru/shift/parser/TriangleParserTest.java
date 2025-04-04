package ru.shift.parser;

import org.junit.jupiter.api.Test;
import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Triangle;

import static org.junit.jupiter.api.Assertions.*;

class TriangleParserTest {
    private static final TriangleParser parser = new TriangleParser();

    @Test
    public void getCode_returnTriangleCode() {
        assertEquals("TRIANGLE", parser.getCode());
    }

    @Test
    public void supports_triangleCode_returnTrue() {
        var data = new ShapeData("TRIANGLE", null);

        assertTrue(parser.supports(data));
    }

    @Test
    public void create_nullParams_throwsException() {
        var data = new ShapeData("TRIANGLE", null);

        assertThrows(NullPointerException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_emptyParams_throwsException() {
        var data = new ShapeData("TRIANGLE", new String[]{});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_onlyOneParam_throwsException() {
        var data = new ShapeData("TRIANGLE", new String[]{"1"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_onlyTwoParams_throwsException() {
        var data = new ShapeData("TRIANGLE", new String[]{"1", "2"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_moreThanThreeParams_throwsException() {
        var data = new ShapeData("TRIANGLE", new String[]{"1", "2", "3", "4"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_invalidParams_throwsException() {
        var data = new ShapeData("TRIANGLE", new String[]{"br", "br", "br"});

        assertThrows(NumberFormatException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_validParams_returnTriangle() {
        var data = new ShapeData("TRIANGLE", new String[]{"2.5", "2.5", "2.5"});

        var result = parser.create(data);

        assertInstanceOf(Triangle.class, result);
    }
}