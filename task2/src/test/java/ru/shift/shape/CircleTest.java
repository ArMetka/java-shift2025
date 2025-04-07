package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircleTest {
    @Test
    public void constructor_valueOutOfRange_throwsException() {
        assertThrows(InvalidShapeParamsException.class,
                () -> new Circle(-23)
        );
    }

    @Test
    public void write_validCircle_writeCorrectly() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        new Circle(23).write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Circle"));
        assertTrue(result.contains("Area: 1661.90 mm^2"));
        assertTrue(result.contains("Perimeter: 144.51 mm"));
        assertTrue(result.contains("Radius: 23.00 mm"));
        assertTrue(result.contains("Diameter: 46.00 mm"));
    }
}