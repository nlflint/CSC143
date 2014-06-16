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
    private Tokenizer tokenizer;
    private VariableRepository variableRepository;
    private ValidationEngine validationEngine;


    public Main() {

        tokenizer = new Tokenizer();
        variableRepository = new VariableRepository();
        validationEngine = new ValidationEngine(variableRepository);
        calculator = new Calculator(tokenizer, variableRepository, validationEngine);
    }

    public void run() {


    }

    public String resolveExpression(String expression) {
        List<Token> tokens = tokenizer.tokenize(expression);
        ValidationResult validationResult = validationEngine.isExpressionValid(tokens);

        if (!validationResult.result) {
            return validationResult.message;
        }

        if (isRemoveVariable(tokens)) {
            VariableToken variable = (VariableToken) tokens.get(1);
            variableRepository.removeVariable(variable.getName());
            return String.format("‘%s’ has been removed!", variable.getName());
        }

        if (isListVariables(tokens)) {
            String output = "";
            List<String> variableNames = variableRepository.getAllVariableNames();
            for (int i = 0; i < variableNames.size() - 1; i++) {
                String variableName = variableNames.get(i);
                double value = variableRepository.getVariableValue(variableName);
                output += String.format("%s = %s; ", variableName, value);
            }

            String lastVariableName = variableNames.get(variableNames.size() - 1);
            double value = variableRepository.getVariableValue(lastVariableName);
            return output + String.format("%s = %s", lastVariableName, value);
        }

        if (containsAssignment(tokens)) {
            int lastIndex = tokens.size();
            List<Token> tokensWithoutAssignment = tokens.subList(2, lastIndex);

            double answer = calculator.calculate(tokensWithoutAssignment);

            VariableToken variable = (VariableToken) tokens.get(0);
            variableRepository.setVariableValue(variable.getName(), answer);

            return String.format("%s = %s",
                    variable.getName(),
                    variableRepository.getVariableValue(variable.getName()));
        }
        else {
            double answer = calculator.calculate(tokens);
            return String.format("%s", answer);
        }
    }

    private boolean isListVariables(List<Token> tokens) {
        return tokens.size() == 1 && tokens.get(0) instanceof ListVariablesToken;
    }

    private boolean isRemoveVariable(List<Token> tokens) {
        return tokens.size() == 2 && tokens.get(0) instanceof RemoveVariableToken;
    }

    private boolean containsAssignment(List<Token> tokens) {
        return tokens.size() > 1 && tokens.get(1) instanceof AssignmentToken;
    }

    public static void main(String[] args) {
        new Main().run();

    }
}
