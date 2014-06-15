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
        assertEquals(validator.getMessage(), "Missing closed parentheses.");
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
        assertEquals(validator.getMessage(), "Redundant closed parentheses.");
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
        assertEquals(validator.getMessage(), "Expect a variable on the LHS.");
    }

    @Test
    public void unexpectedEndOfLine() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("area = pi * radius *");

        // Act
        Validator validator = new Validator();
        boolean isValid = validator.isValid(tokens);

        // Assert
        assertFalse(isValid);
        assertEquals(validator.getMessage(), "Unexpected end of line.");
    }


}
