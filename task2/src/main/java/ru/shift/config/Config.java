package ru.shift.config;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static final Logger log = LogManager.getLogger(Config.class);

    private static final String OUTPUT_FILE_OPT = "o";
    private final List<String> inputFiles = new ArrayList<>();
    private String outputFile;

    public void loadFromArgs(String[] args) throws ParseException {
        Options opt = setupOptions();

        CommandLineParser parser = new DefaultParser(false);
        CommandLine cmd = parser.parse(opt, args);

        readOptions(cmd);
        readInputFiles(cmd);
    }

    private Options setupOptions() {
        Options opt = new Options();

        opt.addOption(OUTPUT_FILE_OPT, true, "specify output file");

        return opt;
    }

    private void readOptions(CommandLine cmd) {
        outputFile = cmd.getOptionValue(OUTPUT_FILE_OPT);
    }

    private void readInputFiles(CommandLine cmd) throws ParseException {
        for (var arg : cmd.getArgList()) {
            if (validateFile(arg)) {
                inputFiles.add(arg);
            } else {
                log.warn("Invalid input file ignored: {}", arg);
            }
        }

        if (inputFiles.isEmpty()) {
            throw new ParseException("No input files specified");
        }
    }

    private boolean validateFile(String filename) {
        return new File(filename).isFile();
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
