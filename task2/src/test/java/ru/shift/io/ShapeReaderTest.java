package ru.shift.io;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.exception.UnknownShapeException;
import ru.shift.shape.Circle;
import ru.shift.shape.Rectangle;
import ru.shift.shape.Shape;
import ru.shift.shape.Triangle;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShapeReaderTest {
    @Test
    public void read_empty_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("");

        assertThrows(UnknownShapeException.class,
                () -> ShapeReader.read(reader)
        );
    }

    @Test
    public void read_unknownShapeCode_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("ELLIPSE");

        assertThrows(UnknownShapeException.class,
                () -> ShapeReader.read(reader)
        );
    }

    @Test
    public void read_validCircleCode_returnCircleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("CIRCLE", "23");

        Shape shape = ShapeReader.read(reader);

        assertInstanceOf(Circle.class, shape);
    }

    @Test
    public void read_validRectangleCode_returnRectangleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("RECTANGLE", "23 23");

        Shape shape = ShapeReader.read(reader);

        assertInstanceOf(Rectangle.class, shape);
    }

    @Test
    public void read_validTriangleCode_returnTriangleInstance() throws IOException, InvalidShapeParamsException, UnknownShapeException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("TRIANGLE", "23 23 23");

        Shape shape = ShapeReader.read(reader);

        assertInstanceOf(Triangle.class, shape);
    }
}