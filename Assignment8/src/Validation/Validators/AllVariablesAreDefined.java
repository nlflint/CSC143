package Validation.Validators;

import Tokenizer.Tokens.AssignmentToken;
import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.VariableToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Created by nathanf on 6/16/2014.
 */
public class AllVariablesAreDefined implements IValidator {
    @Override
    public ValidationResult validate(List<Token> tokens) {
        ValidationResult validationResult = new ValidationResult();

        int startingToken = 0;
        if (expressionUsesAssignmentOperator(tokens))
            startingToken = getIndexOfAssignment(tokens);

        for (int i = startingToken; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token instanceof VariableToken) {
                VariableToken variableToken = (VariableToken) token;
                if (!variableToken.isVariableDefined()) {
                    validationResult.result = false;
                    validationResult.message = String.format("Variable ‘%s’ is undefined.", variableToken.getName());
                    break;
                }
            }
        }
        return validationResult;
    }

    private boolean expressionUsesAssignmentOperator(List<Token> tokens) {
        return getIndexOfAssignment(tokens) != -1;
    }

    private int getIndexOfAssignment(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i) instanceof AssignmentToken)
                return i;
        // if there is no assignment then returns the first token.
        return -1;
    }
}
