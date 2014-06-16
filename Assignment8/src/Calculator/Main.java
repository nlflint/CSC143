package Calculator;

import Tokenizer.Tokenizer;
import Tokenizer.Tokens.*;
import Validation.Validator;

import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Main {
    private Tokenizer tokenizer;
    private Validator validator;
    private Calculator calculator;

    public Main() {
        tokenizer = new Tokenizer();
        validator = new Validator();
        calculator = new Calculator();
    }

    public void run() {


    }

    public String resolveExpression(String expression) {
        List<Token> tokens = tokenizer.tokenize(expression);
        boolean isValid = validator.isExpressionValid(tokens);

        if (!isValid) {
            return validator.getValidationMessage();
        }

        if (isRemoveVariable(tokens)) {
            VariableToken variable = (VariableToken) tokens.get(1);
            variable.removeVariable();
            return String.format("‘%s’ has been removed!", variable.getName());
        }

        if (isListVariables(tokens)) {
            String output = "";
            List<String> variableNames = VariableRepository.getAllVariableNames();
            for (int i = 0; i < variableNames.size() - 1; i++) {
                String variableName = variableNames.get(i);
                double value = VariableRepository.getVariableValue(variableName);
                output += String.format("%s = %s; ", variableName, value);
            }

            String lastVariableName = variableNames.get(variableNames.size() - 1);
            double value = VariableRepository.getVariableValue(lastVariableName);
            return output + String.format("%s = %s", lastVariableName, value);
        }

        if (containsAssignment(tokens)) {
            int lastIndex = tokens.size();
            List<Token> tokensWithoutAssignment = tokens.subList(2, lastIndex);

            double answer = calculator.calculate(tokensWithoutAssignment);

            VariableToken variable = (VariableToken) tokens.get(0);
            variable.setValue(answer);

            return String.format("%s = %s", variable.getName(), variable.getValue());
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
