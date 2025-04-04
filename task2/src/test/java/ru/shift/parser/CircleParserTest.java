package ru.shift.parser;

import org.junit.jupiter.api.Test;
import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Circle;

import static org.junit.jupiter.api.Assertions.*;

class CircleParserTest {
    private static final CircleParser parser = new CircleParser();

    @Test
    public void getCode_returnCircleCode() {
        assertEquals("CIRCLE", parser.getCode());
    }

    @Test
    public void supports_circleCode_returnTrue() {
        var data = new ShapeData("CIRCLE", null);

        assertTrue(parser.supports(data));
    }

    @Test
    public void create_nullParams_throwsException() {
        var data = new ShapeData("CIRCLE", null);

        assertThrows(NullPointerException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_emptyParams_throwsException() {
        var data = new ShapeData("CIRCLE", new String[]{});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_moreThanOneParam_throwsException() {
        var data = new ShapeData("CIRCLE", new String[]{"1", "2"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_invalidParams_throwsException() {
        var data = new ShapeData("CIRCLE", new String[]{"br"});

        assertThrows(NumberFormatException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_validParams_returnCircle() {
        var data = new ShapeData("CIRCLE", new String[]{"2.5"});

        var result = parser.create(data);

        assertInstanceOf(Circle.class, result);
    }
}