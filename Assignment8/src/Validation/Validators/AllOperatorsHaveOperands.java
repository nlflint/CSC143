package Validation.Validators;

import Tokenizer.Tokens.*;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public class AllOperatorsHaveOperands implements IValidator {
    private final String errorMessage = "Unexpected end of line.";

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
