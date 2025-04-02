package ru.shift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.exception.InvalidShapeParamsException;
import ru.shift.exception.UnknownShapeException;
import ru.shift.io.ConsoleOutputStrategy;
import ru.shift.io.FileOutputStrategy;
import ru.shift.io.OutputStrategy;
import ru.shift.io.ShapeReader;
import ru.shift.shape.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ShapesCalculator {
    private static final Logger log = LogManager.getLogger(ShapesCalculator.class);

    private final List<String> inputFiles;
    private final OutputStrategy outputStrategy;

    public ShapesCalculator(List<String> inputFiles) {
        this.inputFiles = inputFiles;
        outputStrategy = new ConsoleOutputStrategy();
    }

    public ShapesCalculator(List<String> inputFiles, String outputFile) {
        this.inputFiles = inputFiles;
        if (outputFile != null) {
            outputStrategy = new FileOutputStrategy(outputFile);
        } else {
            outputStrategy = new ConsoleOutputStrategy();
        }
    }

    public void calculate() {
        try (PrintWriter out = outputStrategy.newPrintWriter()) {
            for (var file : inputFiles) {
                try (BufferedReader in = Files.newBufferedReader(Paths.get(file))) {
                    Shape shape = ShapeReader.read(in);
                    shape.write(out);
                    out.write(System.lineSeparator());
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                } catch (NumberFormatException ex) {
                    log.error("Failed to parse input {} in file {}", ex.getMessage(), file);
                } catch (UnknownShapeException | InvalidShapeParamsException ex) {
                    log.error("{} in file {}", ex.getMessage(), file);
                }
            }
        } catch (IOException ex) {
            log.fatal(ex.getMessage());
        }
    }
}
