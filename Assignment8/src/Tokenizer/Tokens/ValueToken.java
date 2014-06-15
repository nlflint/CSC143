package Tokenizer.Tokens;

/**
 * Created by nate on 6/14/14.
 */
public class ValueToken extends Token {
    private double value;

    public ValueToken(String value) {
        this.value = Double.parseDouble(value.trim());
    }
    @Override
    public double getValue() {
        return value;
    }
}
