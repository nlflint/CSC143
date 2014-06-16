package Tests;

import Tokenizer.Tokens.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import Tokenizer.*;

/**
 * Created by nate on 6/14/14.
 */
public class TokenizerTests {
    @Test
    public void createTokensFromExpression() {
        // Arrange
        String expression = "var = 5.7+pi* (20 / 4.0) -43.2 ";

        // Act
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expression);

        // Assert
        assertTrue(tokens.get(0) instanceof VariableToken);
        assertTrue(tokens.get(1) instanceof AssignmentToken);
        assertTrue(tokens.get(2) instanceof ValueToken);
        assertTrue(tokens.get(3) instanceof PlusToken);
        assertTrue(tokens.get(4) instanceof VariableToken);
        assertTrue(tokens.get(5) instanceof ProductToken);
        assertTrue(tokens.get(6) instanceof OpenParenToken);
        assertTrue(tokens.get(7) instanceof ValueToken);
        assertTrue(tokens.get(8) instanceof QuotientToken);
        assertTrue(tokens.get(9) instanceof ValueToken);
        assertTrue(tokens.get(10) instanceof CloseParenToken);
        assertTrue(tokens.get(11) instanceof MinusToken);
        assertTrue(tokens.get(12) instanceof ValueToken);
    }

    @Test
    public void expressionWithTrailingVariable() {
        // Arrange
        String expression = "var = blue ";

        // Act
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expression);

        // Assert
        assertTrue(tokens.get(0) instanceof VariableToken);
        assertTrue(tokens.get(1) instanceof AssignmentToken);
        assertTrue(tokens.get(2) instanceof VariableToken);
    }

    @Test
    public void expressionWithTrailingOperator() {
        // Arrange
        String expression = "var = blue +         ";

        // Act
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expression);

        // Assert
        assertTrue(tokens.get(0) instanceof VariableToken);
        assertTrue(tokens.get(1) instanceof AssignmentToken);
        assertTrue(tokens.get(2) instanceof VariableToken);
        assertTrue(tokens.get(3) instanceof PlusToken);
    }

    @Test
    public void unrecognizedVariableValue() {
        // Arrange
        String expression = "blue2 + 4";

        // Act
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expression);

        // Assert
        assertTrue(tokens.get(0) instanceof UnknownToken);
        assertTrue(tokens.get(1) instanceof PlusToken);
        assertTrue(tokens.get(2) instanceof ValueToken);
    }

    @Test
    public void unrecognizedOperator() {
        // Arrange
        String expression = "var = 4^2";

        // Act
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expression);

        // Assert
        assertTrue(tokens.get(0) instanceof VariableToken);
        assertTrue(tokens.get(1) instanceof AssignmentToken);
        assertTrue(tokens.get(2) instanceof ValueToken);
        assertTrue(tokens.get(3) instanceof UnknownToken);
        assertTrue(tokens.get(4) instanceof ValueToken);
    }

    @Test
    public void alphasAreVariable() {
        // Arrange
        List<String> areVariables = Arrays.asList("pi", "Size");

        // Act & Assert
        Tokenizer facory = new Tokenizer();
        for (String value : areVariables)
            assertTrue(facory.isVariable(value));

    }

    @Test
    public void anythingNotAlphaIsNotVariable() {
        // Arrange
        List<String> areNotVariables = Arrays.asList("5.0", "six6", "a+","!abc");

        // Act  & Assert
        Tokenizer facory = new Tokenizer();
        for (String value : areNotVariables)
            assertFalse(facory.isVariable(value));
    }

    @Test
    public void isValue() {
        // Arrange
        List<String> areValues = Arrays.asList("123125.2123", "800", ".9123123");

        // Act & Assert
        Tokenizer facory = new Tokenizer();
        for (String value : areValues)
            assertTrue(facory.isValue(value));

    }

    @Test
    public void isNotValue() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231e4", "800abc", "+.", "123.23.123");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isValue(value));

    }

    @Test
    public void isOpenParentheses() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isOpenParentheses("("));
    }

    @Test
    public void isNotOpenParentheses() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "/", "*", "=", ")");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isOpenParentheses(value));

    }

    @Test
    public void isCloseParentheses() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isCloseParentheses(")"));
    }

    @Test
    public void isNotCloseParentheses() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "/", "*", "=", "(");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isCloseParentheses(value));

    }

    @Test
    public void isPlusOperator() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isPlusOperator("+"));
    }

    @Test
    public void isNotPlusOperator() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "-", "/", "*", "=");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isPlusOperator(value));

    }

    @Test
    public void isMinusOperator() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isMinusOperator("-"));
    }

    @Test
    public void isNotMinusOperator() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "/", "*", "=");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isMinusOperator(value));

    }

    @Test
    public void isProductOperator() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isProductOperator("*"));
    }

    @Test
    public void isNotProductOperator() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "-", "/", "=");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isProductOperator(value));

    }

    @Test
    public void isQuotientOperator() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isQuotientOperator("/"));
    }

    @Test
    public void isNotQuotientOperator() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "-", "*", "=");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isQuotientOperator(value));

    }

    @Test
    public void isAssignmentOperator() {
        // Arrange, Act & Assert
        Tokenizer facory = new Tokenizer();
        assertTrue(facory.isAssignmentOperator("="));
    }

    @Test
    public void isNotAssignmentOperator() {
        // Arrange
        List<String> notValues = Arrays.asList("1.231", "abc", "+", "-", "*");

        // Act & Assert
        Tokenizer facory = new Tokenizer();

        for (String value : notValues)
            assertFalse(facory.isAssignmentOperator(value));

    }
}
