package Tokenizer.Tokens;

/**
 * Represents the 4 math operators, add, subtract, multiple and divide: "+", "-", "*", and "/".
 * All four operator token classes inherit from this one.
 */
public abstract class MathOperatorToken extends Token {
    /**
     * Performs a math operation: +, -, *, or /
     * @param first the first operand
     * @param second the second operand
     * @return a value after operating on both operands
     */
    public abstract double operate(double first, double second);
}
