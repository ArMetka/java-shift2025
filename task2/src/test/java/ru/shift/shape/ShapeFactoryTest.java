package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.exception.UnknownShapeException;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShapeFactoryTest {
    @Test
    public void readShape_empty_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("");

        assertThrows(UnknownShapeException.class,
                () -> ShapeFactory.readShape(reader)
        );
    }

    @Test
    public void readShape_unknownShapeCode_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ELLIPSE");

        assertThrows(UnknownShapeException.class,
                () -> ShapeFactory.readShape(reader)
        );
    }

    @Test
    public void readShape_validCircleCode_returnCircleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("CIRCLE", "23");

        Shape shape = ShapeFactory.readShape(reader);

        assertInstanceOf(Circle.class, shape);
    }

    @Test
    public void readShape_validRectangleCode_returnRectangleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("RECTANGLE", "23 23");

        Shape shape = ShapeFactory.readShape(reader);

        assertInstanceOf(Rectangle.class, shape);
    }

    @Test
    public void readShape_validTriangleCode_returnTriangleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("TRIANGLE", "23 23 23");

        Shape shape = ShapeFactory.readShape(reader);

        assertInstanceOf(Triangle.class, shape);
    }
}