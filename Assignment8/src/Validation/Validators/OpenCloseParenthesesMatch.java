package Validation.Validators;

import Tokenizer.Tokens.CloseParenToken;
import Tokenizer.Tokens.OpenParenToken;
import Tokenizer.Tokens.Token;
import Validation.ValidationResult;

import java.util.List;

/**
 * This class validates that the number of close and open parentheses are equal.
 * If the count is not the same then the validation fails with a message
 * explaining the specific problem.
 */
public class OpenCloseParenthesesMatch implements IValidator {
    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
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
