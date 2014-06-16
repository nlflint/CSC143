package Tests;

import Calculator.Validator;
import Tokenizer.Tokens.*;
import Tokenizer.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nate on 6/14/14.
 */
public class ValidatorTest {
    @Test
    public void moreOpenParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + 2");

        // Act
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Missing closed parentheses.", validator.getMessage());
    }

    @Test
    public void moreClosedParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + 2))");

        // Act
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Redundant closed parentheses.", validator.getMessage());
    }

    @Test
    public void mustBeVariableLhsOfAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("pi * 2 = 10");

        // Act
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Expect a variable on the LHS.", validator.getMessage());
    }

    @Test
    public void expressionWithoutAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * 6");

        // Act & Assert
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertTrue(isValid);
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
            assertFalse(validator.isValid(tokens));
            assertEquals("Unexpected end of line.", validator.getMessage());
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
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Variable ‘radius’ is undefined.", validator.getMessage());
    }

    @Test
    public void variableUndefinedWithoutAssignment() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * MyNumber");
        VariableRepository.removeVariable("MyNumber");

        // Act
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Variable ‘MyNumber’ is undefined.", validator.getMessage());
    }

    @Test
    public void variableIsAssigned() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("5 * radius");
        VariableRepository.setVariableValue("radius", 4.0);

        // Act & Assert
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void unrecognizedOperator() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("num = 3 * 4^2");

        // Act & Assert
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Unrecognizable token ‘^’.", validator.getMessage());
    }

    @Test
    public void unrecognizedValueOrVariable() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("area = 2ab");

        // Act & Assert
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals("Unrecognizable token ‘2ab’.", validator.getMessage());
    }


}
