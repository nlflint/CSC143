package Calculator;

import Tokenizer.Tokenizer;
import Tokenizer.Tokens.*;
import Validation.ValidationEngine;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Main {
    private Calculator calculator;

    public Main() {
        Tokenizer tokenizer = new Tokenizer();
        VariableRepository variableRepository = new VariableRepository();
        ValidationEngine validationEngine = new ValidationEngine(variableRepository);
        calculator = new Calculator(tokenizer, variableRepository, validationEngine);
    }

    public void processInputFile() {


    }

    public static void main(String args[]) {
        new Main().processInputFile();
    }

}
