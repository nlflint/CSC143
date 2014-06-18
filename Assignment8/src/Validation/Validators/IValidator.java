package Validation.Validators;

import Tokenizer.Tokens.Token;
import Validation.ValidationResult;

import java.util.List;

/**
 * This interface is implemented by all validators.
 */
public interface IValidator {
    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
    public ValidationResult validate(List<Token> tokens);
}
