package Validation.Validators;

import Tokenizer.Tokens.Token;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public interface IValidator {
    public ValidationResult validate(List<Token> tokens);
}
