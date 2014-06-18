package Validation.Validators;

import Tokenizer.Tokens.*;
import Validation.ValidationResult;

import java.util.List;

/**
 * Validates that each math operator (+,-,*,/), has a variable, value, or appropriate parentheses
 * on it's left and right.
 */
public class AllOperatorsHaveOperands implements IValidator {
    private final String errorMessage = "Unexpected end of line.";

    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
    @Override
    public ValidationResult validate(List<Token> tokens) {
        ValidationResult validationResult = new ValidationResult();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            boolean isMathOperator = isMathOperator(token);
            if (!isMathOperator)
                continue;

            Token previousToken = i < 1 ? null : tokens.get(i - 1);
            Token nextToken = i >= tokens.size() - 1 ? null : tokens.get(i + 1);

            if (isMathOperator(previousToken)
                    || previousToken instanceof OpenParenToken
                    || previousToken == null) {
                validationResult.result = false;
                validationResult.message = errorMessage;
                break;
            }

            if (isMathOperator(nextToken)
                    || nextToken instanceof CloseParenToken
                    || nextToken == null) {
                validationResult.result = false;
                validationResult.message = errorMessage;
                break;
            }
        }
        return validationResult;
    }

    private boolean isMathOperator(Token token) {
        return (token instanceof PlusToken
                || token instanceof MinusToken
                || token instanceof ProductToken
                || token instanceof QuotientToken);
    }
}
