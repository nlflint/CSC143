package Tokenizer.Tokens;

/**
 * Represents addition and performs the operation.
 */
public class PlusToken extends MathOperatorToken {
    /**
     * Adds the first and second
     * @param first the first operand
     * @param second the second operand
     * @return sum of first and second
     */
    @Override
    public double operate(double first, double second) {
        return first + second;
    }
}
