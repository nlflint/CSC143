package Validation.Validators;

import Tokenizer.Tokens.AssignmentToken;
import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.VariableToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Validates that if the statment uses assignment, then there must be just one variable on the left
 * hand side of the assignment operator.
 */
public class VariableExistsOnLhs implements IValidator {
    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
    @Override
    public ValidationResult validate(List<Token> tokens) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.result = true;

        int indexOfAssignment = getIndexOfAssignment(tokens);

        if (indexOfAssignment == -1)
            return validationResult;

        if (!(tokens.get(indexOfAssignment - 1) instanceof VariableToken)) {
            validationResult.result = false;
            validationResult.message = "Expect a variable on the LHS.";
        }

        return validationResult;
    }

    private int getIndexOfAssignment(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i) instanceof AssignmentToken)
                return i;
        // if there is no assignment then returns the first token.
        return -1;
    }
}
