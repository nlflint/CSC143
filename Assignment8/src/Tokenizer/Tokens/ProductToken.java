package Tokenizer.Tokens;

/**
 * Represents multiplication: "*"
 */
public class ProductToken extends MathOperatorToken {
    /**
     * Performs multiplication of first and second
     * @param first the first operand
     * @param second the second operand
     * @return the product of first and second
     */
    @Override
    public double operate(double first, double second) {
        return first * second;
    }
}
