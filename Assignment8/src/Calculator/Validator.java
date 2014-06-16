package Calculator;

import Tokenizer.Tokens.*;

import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Validator {
    private String undefinedVariableName;
    private String unknownTokenName;

    private String message;
    public String getValidationMessage() { return message;
    }

    public boolean isExpressionValid(List<Token> tokens) {
        if (thereAreMoreOpenParenthesesThanClose(tokens)) {
            message = "Missing closed parentheses.";
            return false;
        }

        if (thereAreMoreCloseParenthesesThanOpen(tokens)) {
            message = "Redundant closed parentheses.";
            return false;
        }

        if (expressionUsesAssignmentOperator(tokens) && !variableExistsOnLHS(tokens)) {
            message = "Expect a variable on the LHS.";
            return false;
        }

        if (!allOperatorsHaveOperands(tokens)) {
            message = "Unexpected end of line.";
            return false;
        }

        if (!allTokensAreKnown(tokens)) {
            message = String.format("Unrecognizable token ‘%s’.", unknownTokenName);
            return false;
        }

        if (!allVariableAreDefined(tokens)) {
            message = String.format("Variable ‘%s’ is undefined.", undefinedVariableName);
            return false;
        }

        return true;
    }

    private boolean thereAreMoreOpenParenthesesThanClose(List<Token> tokens) {
        return getOpenParenCount(tokens) > getCloseParenCount(tokens);
    }

    private int getCloseParenCount(List<Token> tokens) {
        int count = 0;
        for (Token token : tokens)
            if (token instanceof CloseParenToken)
                count++;
        return count;
    }

    private int getOpenParenCount(List<Token> tokens) {
        int count = 0;
        for (Token token : tokens)
            if (token instanceof OpenParenToken)
                count++;
        return count;
    }

    private boolean thereAreMoreCloseParenthesesThanOpen(List<Token> tokens) {
        return getOpenParenCount(tokens) < getCloseParenCount(tokens);
    }

    private boolean variableExistsOnLHS(List<Token> tokens) {
        int indexOfAssignment = getIndexOfAssignment(tokens);

        if (indexOfAssignment != 1)
            return false;

        if (!(tokens.get(indexOfAssignment - 1) instanceof VariableToken))
            return false;

        return true;
    }

    private int getIndexOfAssignment(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i) instanceof AssignmentToken)
                return i;
        // if there is no assignment then returns the first token.
        return -1;
    }

    private boolean expressionUsesAssignmentOperator(List<Token> tokens) {
        return getIndexOfAssignment(tokens) != -1;
    }

    private boolean allOperatorsHaveOperands(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            boolean isMathOperator = isMathOperator(token);
            if (!isMathOperator)
                continue;

            Token previousToken = i < 1 ? null : tokens.get(i - 1);
            Token nextToken = i >= tokens.size() - 1 ? null : tokens.get(i + 1);

            if (isMathOperator(previousToken)
                    || previousToken instanceof OpenParenToken
                    || previousToken == null) {
                return false;
            }

            if (isMathOperator(nextToken)
                    || nextToken instanceof CloseParenToken
                    || nextToken == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isMathOperator(Token token) {
        return (token instanceof PlusToken
                || token instanceof MinusToken
                || token instanceof ProductToken
                || token instanceof QuotientToken);
    }

    private boolean allVariableAreDefined(List<Token> tokens) {
        // When assignment operator is used, its okay for the assigning variable to
        // be undefined. If assignment is not used, then just start at first token.
        int startingToken = 0;
        if (expressionUsesAssignmentOperator(tokens))
            startingToken = getIndexOfAssignment(tokens);

        for (int i = startingToken; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token instanceof VariableToken) {
                VariableToken variableToken = (VariableToken) token;
                if (!variableToken.isVariableDefined()) {
                    undefinedVariableName = variableToken.getName();
                    return false;
                }

            }

        }
        return true;
    }

    private boolean allTokensAreKnown(List<Token> tokens) {
        for (Token token : tokens) {
            if (token instanceof UnknownToken) {
                UnknownToken unknownToken = (UnknownToken) token;
                unknownTokenName = unknownToken.getUnkownTokenName();
                return false;
            }
        }
        return true;
    }
}
