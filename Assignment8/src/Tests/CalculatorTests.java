package Tests;

import Calculator.Calculator;
import Tokenizer.Tokenizer;
import Tokenizer.Tokens.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nate on 6/15/14.
 */
public class CalculatorTests {
    @Test
    public void addition() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("2 + 2");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(4.0, value, 0.0);
    }

    @Test
    public void subtraction() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 - 2");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(28.0, value, 0.0);
    }

    @Test
    public void addAndsubtract() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 - 2 + 4 + 34 - 2 - 10");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(54.0, value, 0.0);
    }

    @Test
    public void multiply() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("4 * 5");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(20.0, value, 0.0);
    }

    @Test
    public void divide() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(5.0, value, 0.0);
    }

    @Test
    public void multipleAndDivide() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6 * 4 / 10 * 3 * 4 / 8");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(3.0, value, 0.0);
    }

    @Test
    public void multipleDivideAddAndSubtract() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6 * 4 + 10 * 3 - 8 / 4");

        // Act
        Calculator calculator = new Calculator();
        double value = calculator.evaluate(tokens);

        // Assert
        assertEquals(100.0, value, 0.0);
    }
}
