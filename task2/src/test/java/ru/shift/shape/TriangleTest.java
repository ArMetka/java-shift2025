package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.io.TriangleReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TriangleTest {
    @Test
    public void read_empty_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_inputOneValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_inputTwoValues_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_moreThanThreeValues_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 23 23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_incorrectValue_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 br 23");

        assertThrows(NumberFormatException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_valueOutOfRange_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("-23 23 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_invalidTriangle_throwsException() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("1 1 23");

        assertThrows(InvalidShapeParamsException.class,
                () -> TriangleReader.read(reader)
        );
    }

    @Test
    public void read_validEquilateralTriangle_writeCorrectly() throws IOException, InvalidShapeParamsException, NoSuchFieldException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("23 23 23");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Triangle triangle = TriangleReader.read(reader);
        triangle.write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Triangle"));
        assertTrue(result.contains("Area: 229.06 mm^2"));
        assertTrue(result.contains("Perimeter: 69.00 mm"));
        assertTrue(result.contains("Length of side 1: 23.00 mm"));
        assertTrue(result.contains("Opposite angle of side 1: 1.05 rad"));
        assertTrue(result.contains("Length of side 2: 23.00 mm"));
        assertTrue(result.contains("Opposite angle of side 2: 1.05 rad"));
        assertTrue(result.contains("Length of side 3: 23.00 mm"));
        assertTrue(result.contains("Opposite angle of side 3: 1.05 rad"));
    }

    @Test
    public void read_validArbitraryTriangle_writeCorrectly() throws IOException, InvalidShapeParamsException, NoSuchFieldException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("42 88 56");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Triangle triangle = TriangleReader.read(reader);
        triangle.write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Triangle"));
        assertTrue(result.contains("Area: 936.73 mm^2"));
        assertTrue(result.contains("Perimeter: 186.00 mm"));
        assertTrue(result.contains("Length of side 1: 42.00 mm"));
        assertTrue(result.contains("Opposite angle of side 1: 0.39 rad"));
        assertTrue(result.contains("Length of side 2: 88.00 mm"));
        assertTrue(result.contains("Opposite angle of side 2: 2.22 rad"));
        assertTrue(result.contains("Length of side 3: 56.00 mm"));
        assertTrue(result.contains("Opposite angle of side 3: 0.53 rad"));
    }
}