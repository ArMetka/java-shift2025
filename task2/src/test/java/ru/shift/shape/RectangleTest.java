package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RectangleTest {
    @Test
    public void read_empty_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("");

        assertThrows(InvalidShapeParamsException.class,
                () -> Rectangle.read(reader)
        );
    }

    @Test
    public void read_oneValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Rectangle.read(reader)
        );
    }

    @Test
    public void read_moreThanTwoValues_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Rectangle.read(reader)
        );
    }

    @Test
    public void read_incorrectValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("br 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Rectangle.read(reader)
        );
    }

    @Test
    public void read_valueOutOfRange_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("-23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Rectangle.read(reader)
        );
    }

    @Test
    public void read_validRectangle_writeCorrectly() throws IOException, InvalidShapeParamsException, NoSuchFieldException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 52");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Rectangle rectangle = Rectangle.read(reader);
        rectangle.write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Rectangle"));
        assertTrue(result.contains("Area: 1196.00 mm^2"));
        assertTrue(result.contains("Perimeter: 104.00 mm"));
        assertTrue(result.contains("Length of diagonal: 56.86 mm"));
        assertTrue(result.contains("Length: 52.00 mm"));
        assertTrue(result.contains("Width: 23.00 mm"));
    }
}