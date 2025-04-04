package ru.shift.parser;

import org.junit.jupiter.api.Test;
import ru.shift.dto.ShapeData;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.shape.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class RectangleParserTest {
    private static final RectangleParser parser = new RectangleParser();

    @Test
    public void getCode_returnRectangleCode() {
        assertEquals("RECTANGLE", parser.getCode());
    }

    @Test
    public void supports_rectangleCode_returnTrue() {
        var data = new ShapeData("RECTANGLE", null);

        assertTrue(parser.supports(data));
    }

    @Test
    public void create_nullParams_throwsException() {
        var data = new ShapeData("RECTANGLE", null);

        assertThrows(NullPointerException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_emptyParams_throwsException() {
        var data = new ShapeData("RECTANGLE", new String[]{});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_onlyOneParam_throwsException() {
        var data = new ShapeData("RECTANGLE", new String[]{"1"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_moreThanTwoParams_throwsException() {
        var data = new ShapeData("RECTANGLE", new String[]{"1", "2", "3"});

        assertThrows(InvalidShapeParamsException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_invalidParams_throwsException() {
        var data = new ShapeData("RECTANGLE", new String[]{"br", "br"});

        assertThrows(NumberFormatException.class,
                () -> parser.create(data)
        );
    }

    @Test
    public void create_validParams_returnRectangle() {
        var data = new ShapeData("RECTANGLE", new String[]{"2.5", "2.5"});

        var result = parser.create(data);

        assertInstanceOf(Rectangle.class, result);
    }
}