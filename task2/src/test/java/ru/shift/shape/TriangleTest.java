package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TriangleTest {
    @Test
    public void constructor_valueOutOfRange_throwsException() {
        assertThrows(InvalidShapeParamsException.class,
                () -> new Triangle(-23, 23, 23)
        );
    }

    @Test
    public void constructor_invalidTriangle_throwsException() {
        assertThrows(InvalidShapeParamsException.class,
                () -> new Triangle(1, 1, 23)
        );
    }

    @Test
    public void write_validEquilateralTriangle_writeCorrectly() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        new Triangle(23, 23, 23).write(printWriter);

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
    public void write_validArbitraryTriangle_writeCorrectly() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        new Triangle(42, 88, 56).write(printWriter);

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