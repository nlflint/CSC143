package Validation.Validators;

import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.UnknownToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public class AllTokensAreKnown implements IValidator {
    @Override
    public ValidationResult validate(List<Token> tokens) {
        ValidationResult validationResult = new ValidationResult();

        for (Token token : tokens) {
            if (token instanceof UnknownToken) {
                UnknownToken unknownToken = (UnknownToken) token;
                validationResult.result = false;
                validationResult.message = String.format("Unrecognizable token ‘%s’.", unknownToken.getUnkownTokenName());
                break;
            }
        }
        return validationResult;
    }
}
