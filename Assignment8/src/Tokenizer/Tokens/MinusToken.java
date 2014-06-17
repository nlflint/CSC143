package Tokenizer.Tokens;

/**
 * Represents subtraction: "-"
 * Actually performs subtraction too.
 */
public class MinusToken extends MathOperatorToken {
    /**
     * Subtracts second from the first.
     * @param first the first operand
     * @param second the second operand
     * @return difference of first and second
     */
    @Override
    public double operate(double first, double second) {
        return first - second;
    }
}
