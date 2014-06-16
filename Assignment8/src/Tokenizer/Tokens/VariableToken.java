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

    public boolean isVariableDefined() {
        return VariableRepository.isVariableDefined(name);
    }

    @Override
    public double getValue() {
        if (!VariableRepository.isVariableDefined(name))
            return Double.NaN;

        return VariableRepository.getVariableValue(name);
    }

    public void setValue(double value) {
        VariableRepository.setVariableValue(name, value);
    }

    public void removeVariable() {
        VariableRepository.removeVariable(name);
    }
}
