package Validation.Validators;

import Tokenizer.Tokens.AssignmentToken;
import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.VariableToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public class VariableExistsOnLhs implements IValidator {
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
