package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.exception.UnknownShapeException;
import ru.shift.shape.Shape;
import ru.shift.shape.ShapeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ShapesCalculator {
    private static final Logger log = LogManager.getLogger(ShapesCalculator.class);

    private final List<String> inputFiles;
    private String outputFile;

    public ShapesCalculator(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public ShapesCalculator(List<String> inputFiles, String outputFile) {
        this.inputFiles = inputFiles;
        this.outputFile = outputFile;
    }

    public void calculate() {
        PrintWriter output = null;
        try {
            if (outputFile != null) {
                output = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFile)));
            } else {
                output = new PrintWriter(System.out, true);
            }

            for (var inputFile : inputFiles) {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
                    Shape shape = ShapeFactory.readShape(reader);
                    shape.write(output);
                    output.write(System.lineSeparator());
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                } catch (UnknownShapeException | InvalidShapeParamsException ex) {
                    log.error("{} in file {}", ex.getMessage(), inputFile);
                }
            }
        } catch (IOException ex) {
            log.fatal(ex.getMessage());
        } finally {
            if (outputFile != null && output != null) {
                output.close();
            }
        }
    }
}
