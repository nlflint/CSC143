package Validation;

import Program.VariableRepository;
import Tokenizer.Tokens.*;
import Validation.Validators.*;

import java.util.Arrays;
import java.util.List;

/**
 * Validates a list of tokens to make sure it contains no logical errors.
 */
public class ValidationEngine {
    private List<IValidator> validators;

    /**
     * Constructor. Initializes all the validations that will be run.
     * @param variableRepository a repository to use when validating variables contain data
     */
    public ValidationEngine(VariableRepository variableRepository) {
        validators = Arrays.asList(
                new OpenCloseParenthesesMatch(),
                new AllOperatorsHaveOperands(),
                new VariableExistsOnLhs(),
                new AllOperatorsHaveOperands(),
                new AllTokensAreKnown(),
                new AllVariablesAreDefined(variableRepository)
        );
    }

    /**
     * Validates the given list of tokens and returns a validation result
     * @param tokens a list of tokens to be validated
     * @return a validation results indicating the results.
     */
    public ValidationResult isExpressionValid(List<Token> tokens) {
        for (IValidator validator : validators) {
            ValidationResult validationResult = validator.validate(tokens);
            if (!validationResult.result)
                return validationResult;
        }

        return new ValidationResult();
    }
}
