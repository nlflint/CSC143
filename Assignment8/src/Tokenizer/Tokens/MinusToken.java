package Tokenizer.Tokens;

import Tokenizer.Tokenizer;

/**
 * Created by nate on 6/14/14.
 */
public class MinusToken extends OperatorToken {
    @Override
    public double operate(double first, double second) {
        return first - second;
    }
}
