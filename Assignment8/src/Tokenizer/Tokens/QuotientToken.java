package Tokenizer.Tokens;

/**
 * Represents division: "/"
 */
public class QuotientToken extends MathOperatorToken {
    /**
     * Divides the first by the second
     * @param first the first operand
     * @param second the second operand
     * @return quotient of first and second
     */
    @Override
    public double operate(double first, double second) {
        return first / second;
    }
}
