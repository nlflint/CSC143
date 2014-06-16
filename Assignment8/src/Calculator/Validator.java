package Calculator;

import Tokenizer.Tokens.*;

import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Validator {

    private String message;
    public String getMessage() { return message;
    }
    public boolean isValid(List<Token> tokens) {
        if (thereAreMoreOpenParenthesesThanClose(tokens)) {
            message = "Missing closed parentheses.";
            return false;
        }

        if (thereAreMoreCloseParenthesesThanOpen(tokens)) {
            message = "Redundant closed parentheses.";
            return false;
        }

        if (thereIsNotVariableOnLHS(tokens)) {
            message = "Expect a variable on the LHS.";
            return false;
        }

        if (anyOperatorsMissingOperands(tokens)) {
            message = "Unexpected end of line.";
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

    private boolean thereIsNotVariableOnLHS(List<Token> tokens) {
        int indexOfAssignment = getIndexOfAssignment(tokens);

        if (indexOfAssignment != 1)
            return true;

        if (!(tokens.get(indexOfAssignment - 1) instanceof VariableToken))
            return true;

        return false;
    }

    private int getIndexOfAssignment(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i) instanceof AssignmentToken)
                return i;
        return -1;
    }

    private boolean anyOperatorsMissingOperands(List<Token> tokens) {
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
                return true;
            }

            if (isMathOperator(nextToken)
                    || nextToken instanceof CloseParenToken
                    || nextToken == null) {
                return true;
            }
        }
        return false;
    }

    private boolean isMathOperator(Token token) {
        return (token instanceof PlusToken
                || token instanceof MinusToken
                || token instanceof ProductToken
                || token instanceof QuotientToken);
    }




}
