package Tokenizer.Tokens;

/**
 * Created by nate on 6/14/14.
 */
public class QuotientToken extends OperatorToken {
    @Override
    public double operate(double first, double second) {
        return first / second;
    }
}
