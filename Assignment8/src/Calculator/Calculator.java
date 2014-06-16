package Calculator;

import Tokenizer.Tokens.MinusToken;
import Tokenizer.Tokens.PlusToken;
import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.ValueToken;

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
        int plusIndex = findIndexOfFirstPlus(tokens);
        if (plusIndex >= 0) {
            List<Token> leftTokens = getTokensLeftOfIndex(plusIndex, tokens);
            List<Token> rightTokens = getTokensRightOfIndex(plusIndex, tokens);
            return recursiveCalculate(leftTokens) + recursiveCalculate(rightTokens);
        }

        // find first -
        int minusIndex = findIndexOfFirstMinus(tokens);
        if (minusIndex >= 0) {
            List<Token> leftTokens = getTokensLeftOfIndex(minusIndex, tokens);
            List<Token> rightTokens = getTokensRightOfIndex(minusIndex, tokens);
            return recursiveCalculate(leftTokens) - recursiveCalculate(rightTokens);
        }

        return tokens.get(0).getValue();
    }

    private int findIndexOfFirstMinus(List<Token> tokens) {
        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            Token token = tokens.get(i);
            if (token instanceof MinusToken)
                return i;
        }
        return -1;
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

    private int findIndexOfFirstPlus(List<Token> tokens) {
        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            Token token = tokens.get(i);
            if (token instanceof PlusToken)
                return i;
        }
        return -1;
    }

}
