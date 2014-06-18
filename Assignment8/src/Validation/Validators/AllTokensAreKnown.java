package Validation.Validators;

import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.UnknownToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Validates there are no UnknownToken types and prints an error message if it finds one. Tokens
 * like "^" and or "abc123" are detected during tokenization and classified as unknown tokens.
 */
public class AllTokensAreKnown implements IValidator {
    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
    @Override
    public ValidationResult validate(List<Token> tokens) {
        ValidationResult validationResult = new ValidationResult();

        for (Token token : tokens) {
            if (token instanceof UnknownToken) {
                UnknownToken unknownToken = (UnknownToken) token;
                validationResult.result = false;
                validationResult.message = String.format("Unrecognizable token ‘%s’.", unknownToken.getUnknownTokenName());
                break;
            }
        }
        return validationResult;
    }
}
