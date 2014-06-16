package Tests;

import Validation.ValidationResult;
import Validation.Validator;
import Tokenizer.Tokens.*;
import Tokenizer.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nate on 6/14/14.
 */
public class ValidatorTests {
    @Test
    public void moreOpenParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + 2");

        // Act
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Missing closed parentheses.", validationResult.message);
    }

    @Test
    public void moreClosedParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + 2))");

        // Act
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Redundant closed parentheses.", validationResult.message);
    }

    @Test
    public void mustBeVariableLhsOfAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("pi * 2 = 10");

        // Act
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Expect a variable on the LHS.", validationResult.message);
    }

    @Test
    public void expressionWithoutAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * 6");

        // Act & Assert
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertTrue(validationResult.result);
    }

    @Test
    public void unexpectedEndOfLine() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<List<Token>> tokenListList = Arrays.asList(
                tokenizer.tokenize("area = pi * radius *"),
                tokenizer.tokenize("area = pi + (* radius)"),
                tokenizer.tokenize("area = pi + (radius *)"));

        // Act & Assert
        for (List<Token> tokens : tokenListList) {
            Validator validator = new Validator();
            ValidationResult validationResult = validator.isExpressionValid(tokens);
            assertFalse(validationResult.result);
            assertEquals("Unexpected end of line.", validationResult.message);
        }
    }

    @Test
    public void variableUndefined() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("var = 5 * radius");
        VariableRepository.removeVariable("radius");

        // Act & Assert
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Variable ‘radius’ is undefined.", validationResult.message);
    }

    @Test
    public void variableUndefinedWithoutAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * MyNumber");
        VariableRepository.removeVariable("MyNumber");

        // Act
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Variable ‘MyNumber’ is undefined.", validationResult.message);
    }

    @Test
    public void variableIsAssigned() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * radius");
        VariableRepository.setVariableValue("radius", 4.0);

        // Act & Assert
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertTrue(validationResult.result);
    }

    @Test
    public void unrecognizedOperator() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("num = 3 * 4^2");

        // Act & Assert
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Unrecognizable token ‘^’.", validationResult.message);
    }

    @Test
    public void unrecognizedValueOrVariable() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("area = 2ab");

        // Act & Assert
        Validator validator = new Validator();
        ValidationResult validationResult = validator.isExpressionValid(tokens);

        // Assert
        assertFalse(validationResult.result);
        assertEquals("Unrecognizable token ‘2ab’.", validationResult.message);
    }


}
