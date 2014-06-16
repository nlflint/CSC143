package Calculator;

import Tokenizer.Tokens.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nate on 6/15/14.
 */
public class Calculator {
    public double evaluate(List<Token> tokens) {
        return recursiveCalculate(tokens);
    }

    private double recursiveCalculate(List<Token> tokens) {
        // find first +
        int addOrSubtractIndex = findIndexOfLastPlusOrMinus(tokens);
        if (addOrSubtractIndex >= 0) {
            OperatorToken token = (OperatorToken) tokens.get(addOrSubtractIndex);
            List<Token> leftTokens = getTokensLeftOfIndex(addOrSubtractIndex, tokens);
            List<Token> rightTokens = getTokensRightOfIndex(addOrSubtractIndex, tokens);
            return token.operate(recursiveCalculate(leftTokens), recursiveCalculate(rightTokens));
        }

        int multipleOrDivideIndex = findIndexOfLastMultiplyOrDivide(tokens);
        if (multipleOrDivideIndex >= 0) {
            OperatorToken token = (OperatorToken) tokens.get(multipleOrDivideIndex);
            List<Token> leftTokens = getTokensLeftOfIndex(multipleOrDivideIndex, tokens);
            List<Token> rightTokens = getTokensRightOfIndex(multipleOrDivideIndex, tokens);
            return token.operate(recursiveCalculate(leftTokens), recursiveCalculate(rightTokens));
        }

        if (tokens.size() > 1 && expressionContainedInParentheses(tokens))  {
            List<Token> parenthesesStripped = stripOuterParentheses(tokens);
            return recursiveCalculate(parenthesesStripped);
        }

        return tokens.get(0).getValue();
    }

    private List<Token> stripOuterParentheses(List<Token> tokens) {
        int lastIndex = tokens.size() - 1;
        List<Token> newList = tokens.subList(1, lastIndex);
        return newList;
    }

    private boolean expressionContainedInParentheses(List<Token> tokens) {
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

    private int findIndexOfLastPlusOrMinus(List<Token> tokens) {

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

    private int findIndexOfLastMultiplyOrDivide(List<Token> tokens) {
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
