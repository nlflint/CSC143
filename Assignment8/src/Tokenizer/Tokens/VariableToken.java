package Tokenizer.Tokens;


/**
 * Created by nate on 6/14/14.
 */
public class VariableToken extends Token {
    private String name;

    public VariableToken(String name) {
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return Double.NaN;
    }
}
