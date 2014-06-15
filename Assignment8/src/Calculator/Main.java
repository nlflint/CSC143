package Calculator;

import Tokenizer.Tokenizer;
import Tokenizer.Tokens.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Main {
    public static void main(String[] args) {
        List<String> expressions = Arrays.asList("var = 1+2 * (3+10)", "4+5", "var = 2^2");


        String output;

        for (String expression : expressions) {
            Tokenizer tokenizer = new Tokenizer();
            List<Token> tokens = tokenizer.tokenize(expression);

            Validator validator = new Validator();
            boolean isValid = validator.isValid(tokens);

            if (!isValid) {
                output = validator.getMessage();
                continue;
            }
        }
    }
}
