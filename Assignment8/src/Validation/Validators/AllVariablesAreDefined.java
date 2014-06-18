package Validation.Validators;

import Program.VariableRepository;
import Tokenizer.Tokens.AssignmentToken;
import Tokenizer.Tokens.Token;
import Tokenizer.Tokens.VariableToken;
import Validation.ValidationResult;

import java.util.List;

/**
 * Validates that all Variable tokens on the right side of the assignment statement
 * have a value defined in thh variable repository.  When setting up the calculator
 * is is important that the calculator and this validator are using the same instance
 * of the variable repository.
 */
public class AllVariablesAreDefined implements IValidator {
    private VariableRepository variableRepository;

    /**
     * Constructor takes a variable repository that is used to look up values.
     * @param variableRepository the variable repository for looking up values.
     */
    public AllVariablesAreDefined(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Runs validation on the given set of tokens.
     * @param tokens a list of tokens to validate
     * @return the results of the validation
     */
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
                if (!variableRepository.isVariableDefined(variableToken.getName())) {
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
