package Validation.Validators;

import Tokenizer.Tokens.CloseParenToken;
import Tokenizer.Tokens.OpenParenToken;
import Tokenizer.Tokens.Token;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public class OpenCloseParenthesesMatch implements IValidator {
    @Override
    public ValidationResult validate(List<Token> tokens) {
        int closeParenCount = getCloseParenCount(tokens);
        int openParenCount = getOpenParenCount(tokens);

        ValidationResult validationResult = new ValidationResult();
        validationResult.result = closeParenCount == openParenCount;

        if (openParenCount > closeParenCount)
            validationResult.message = "Missing closed parentheses.";
        else if (openParenCount < closeParenCount)
            validationResult.message = "Redundant closed parentheses.";

        return validationResult;
    }

    private int getCloseParenCount(List<Token> tokens) {
        int count = 0;
        for (Token token : tokens)
            if (token instanceof CloseParenToken)
                count++;
        return count;
    }

    private int getOpenParenCount(List<Token> tokens) {
        int count = 0;
        for (Token token : tokens)
            if (token instanceof OpenParenToken)
                count++;
        return count;
    }
}
