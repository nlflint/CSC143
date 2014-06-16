package Program;

import Tokenizer.Tokens.*;
import Tokenizer.*;
import Validation.ValidationEngine;
import Validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nate on 6/15/14.
 */
public class Calculator {
    private Tokenizer tokenizer;
    private VariableRepository variableRepository;
    private ValidationEngine validationEngine;

    public Calculator(Tokenizer tokenizer, VariableRepository variableRepository, ValidationEngine validationEngine) {
        this.tokenizer = tokenizer;
        this.variableRepository = variableRepository;
        this.validationEngine = validationEngine;
    }

    public String calculate(String expression) {
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

            double answer = resolveExpression(tokensWithoutAssignment);

            VariableToken variable = (VariableToken) tokens.get(0);
            variableRepository.setVariableValue(variable.getName(), answer);

            return String.format("%s = %s",
                    variable.getName(),
                    variableRepository.getVariableValue(variable.getName()));
        }
        else {
            double answer = resolveExpression(tokens);
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

    public double resolveExpression(List<Token> tokens) {
        // find first +
        int addOrSubtractIndex = findIndexOfRightMostPlusOrMinus(tokens);
        if (addOrSubtractIndex >= 0)
            return splitTokensAndRecurse(tokens, addOrSubtractIndex);

        int multipleOrDivideIndex = findIndexOfRightMostMultiplyOrDivide(tokens);
        if (multipleOrDivideIndex >= 0)
            return splitTokensAndRecurse(tokens, multipleOrDivideIndex);

        if (tokens.size() > 1 && isEntireExpressionContainedInParentheses(tokens))
            return resolveExpression(stripOuterParentheses(tokens));

        Token token = tokens.get(0);
        if (token instanceof VariableToken)
            return variableRepository.getVariableValue(((VariableToken) token).getName());
        else
            return token.getValue();
    }

    private double splitTokensAndRecurse(List<Token> tokens, int index) {
        MathOperatorToken token = (MathOperatorToken) tokens.get(index);
        List<Token> leftTokens = getTokensLeftOfIndex(index, tokens);
        List<Token> rightTokens = getTokensRightOfIndex(index, tokens);
        return token.operate(resolveExpression(leftTokens), resolveExpression(rightTokens));
    }

    private List<Token> stripOuterParentheses(List<Token> tokens) {
        int lastIndex = tokens.size() - 1;
        List<Token> newList = tokens.subList(1, lastIndex);
        return newList;
    }

    private boolean isEntireExpressionContainedInParentheses(List<Token> tokens) {
        int lastIndex = tokens.size() - 1;
        return (tokens.get(0) instanceof OpenParenToken)
                && (tokens.get(lastIndex) instanceof CloseParenToken);

    }

    private List<Token> getTokensRightOfIndex(int plusIndex, List<Token> tokens) {
        ArrayList<Token> rightTokens = new ArrayList<Token>();

        for (int i = plusIndex + 1; i < tokens.size(); i++) {
            rightTokens.add(tokens.get(i));
        }
        return rightTokens;
    }

    private List<Token> getTokensLeftOfIndex(int plusIndex, List<Token> tokens) {
        ArrayList<Token> leftTokens = new ArrayList<Token>();

        for (int i = 0; i < plusIndex; i++) {
            leftTokens.add(tokens.get(i));
        }
        return leftTokens;
    }

    private int findIndexOfRightMostPlusOrMinus(List<Token> tokens) {

        int lastIndex = tokens.size() - 1;
        int parenDepth = 0;

        for (int i = lastIndex; i > 0; i--) {
            Token token = tokens.get(i);
            if (token instanceof CloseParenToken)
                parenDepth++;
            if (token instanceof OpenParenToken)
                parenDepth--;
            if ((token instanceof PlusToken || token instanceof MinusToken)
                    && parenDepth == 0)
                return i;
        }
        return -1;
    }

    private int findIndexOfRightMostMultiplyOrDivide(List<Token> tokens) {
        int lastIndex = tokens.size() - 1;
        int parenDepth = 0;

        for (int i = lastIndex; i > 0; i--) {
            Token token = tokens.get(i);
            if (token instanceof CloseParenToken)
                parenDepth++;
            if (token instanceof OpenParenToken)
                parenDepth--;
            if ((token instanceof ProductToken || token instanceof QuotientToken)
                    && parenDepth == 0)
                return i;
        }
        return -1;
    }

}
