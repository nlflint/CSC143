package Tokenizer.Tokens;

/**
 * Created by nate on 6/15/14.
 */
public abstract class OperatorToken extends Token {
    public abstract double operate(double first, double second);
}
