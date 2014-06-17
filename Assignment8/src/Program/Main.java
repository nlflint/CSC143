package Program;

import Tokenizer.Tokenizer;
import Validation.ValidationEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class that starts and runs the calculator
 */
public class Main {
    private Calculator calculator;

    /**
     * Setups up a calculator and its dependencies
     */
    public Main() {
        Tokenizer tokenizer = new Tokenizer();
        VariableRepository variableRepository = new VariableRepository();
        ValidationEngine validationEngine = new ValidationEngine(variableRepository);
        calculator = new Calculator(tokenizer, variableRepository, validationEngine);
    }

    /**
     * Executes all calcuations in the input file, and outputs the answer into another file.
     */
    public void run() {
        final String inputFilename = "calculator.input";
        final String outputFilename = "calculator.output";

        List<String> inputContents = readFileContents(inputFilename);
        List<String> outputContents = new ArrayList<String>();

        for (String expression : inputContents)
            outputContents.add(calculator.calculate(expression));

        writeFileContents(outputFilename, outputContents);



    }
    // Writes the givens strings to lines of the given file.
    private void writeFileContents(String outputFilename, List<String> fileOutputContents) {
        String fullFileName = getFullFilePath(outputFilename);

        BufferedWriter bufferedWriter;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fullFileName));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find input file: " + fullFileName);
        } catch (IOException e) {
            throw new RuntimeException("Error creating file: " + fullFileName);
        }

        for(String content : fileOutputContents) {
            try {
                bufferedWriter.write(content);
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Error while writing to file: " + fullFileName);
            }

        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to file: " + fullFileName);
        }

    }

    // Reads the given file and returns all lines as a list of strings
    private List<String> readFileContents(String inputFilename) {

        String fullFileName = getFullFilePath(inputFilename);
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(fullFileName));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find input file: " + fullFileName);
        }

        List<String> fileContents = new ArrayList<String>();

        String fileLine;
        do {
            try {
                fileLine = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Error while reading contents of file:" + fullFileName);
            }
            if (fileLine != null && fileLine.trim().length() > 0)
                fileContents.add(fileLine);
        } while (fileLine != null);

        return fileContents;
    }

    // Pre-pends the working directory to the given file name.
    private String getFullFilePath(String inputFileName) {
        final String configFileName = inputFileName;
        File configFile = new File(configFileName);
        return configFile.getAbsolutePath();
    }

    /**
     * Application method, inputs calculations from a file and outputs to another file.
     * @param args command line arguments. None used.
     */
    public static void main(String args[]) {
        new Main().run();
    }

}
