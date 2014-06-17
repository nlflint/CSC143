package Tokenizer.Tokens;

/**
 * Represents a number like: "5" or "12.53"
 */
public class NumberToken extends Token {
    private double value;

    /**
     * Constructor. Saves the value of this number.
     * @param value the value of the number
     */
    public NumberToken(String value) {
        this.value = Double.parseDouble(value.trim());
    }

    /**
     * Gets the value of this number
     * @return the value of the number
     */
    public double getValue() {
        return value;
    }
}
