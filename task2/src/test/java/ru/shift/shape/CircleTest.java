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

class CircleTest {
    @Test
    public void read_empty_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("");

        assertThrows(InvalidShapeParamsException.class,
                () -> Circle.read(reader)
        );
    }

    @Test
    public void read_moreThanOneValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Circle.read(reader)
        );
    }

    @Test
    public void read_incorrectValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("br");

        assertThrows(InvalidShapeParamsException.class,
                () -> Circle.read(reader)
        );
    }

    @Test
    public void read_valueOutOfRange_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("-23");

        assertThrows(InvalidShapeParamsException.class,
                () -> Circle.read(reader)
        );
    }

    @Test
    public void read_validCircle_writeCorrectly() throws IOException, InvalidShapeParamsException, NoSuchFieldException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Circle circle = Circle.read(reader);
        circle.write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Circle"));
        assertTrue(result.contains("Area: 1661.90 mm^2"));
        assertTrue(result.contains("Perimeter: 144.51 mm"));
        assertTrue(result.contains("Radius: 23.00 mm"));
        assertTrue(result.contains("Diameter: 46.00 mm"));
    }
}