package ru.shift.shape;

import org.junit.jupiter.api.Test;
import ru.shift.exception.InvalidShapeParamsException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RectangleTest {
    @Test
    public void constructor_valueOutOfRange_throwsException() {
        assertThrows(InvalidShapeParamsException.class,
                () -> new Rectangle(-23, 23)
        );
    }

    @Test
    public void write_validRectangle_writeCorrectly() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        new Rectangle(23, 52).write(printWriter);

        String result = stringWriter.toString();
        assertTrue(result.contains("Shape type: Rectangle"));
        assertTrue(result.contains("Area: 1196.00 mm^2"));
        assertTrue(result.contains("Perimeter: 150.00 mm"));
        assertTrue(result.contains("Length of diagonal: 56.86 mm"));
        assertTrue(result.contains("Length: 52.00 mm"));
        assertTrue(result.contains("Width: 23.00 mm"));
    }
}