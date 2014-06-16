package Validation;

import Tokenizer.Tokens.*;
import Validation.Validators.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nate on 6/14/14.
 */
public class Validator {
    private List<IValidator> validators;

    public Validator() {
        validators = Arrays.asList(
                new OpenCloseParenthesesMatch(),
                new AllOperatorsHaveOperands(),
                new VariableExistsOnLhs(),
                new AllOperatorsHaveOperands(),
                new AllTokensAreKnown(),
                new AllVariablesAreDefined()
        );
    }
    public ValidationResult isExpressionValid(List<Token> tokens) {
        for (IValidator validator : validators) {
            ValidationResult validationResult = validator.validate(tokens);
            if (!validationResult.result)
                return validationResult;
        }

        return new ValidationResult();
    }
}
