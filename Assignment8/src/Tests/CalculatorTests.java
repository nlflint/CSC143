package Tests;

import Program.VariableRepository;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;
import Program.Calculator;
import Tokenizer.Tokenizer;
import Tokenizer.Tokens.Token;

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
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(4.0, value, 0.0);
    }

    @Test
    public void subtraction() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 - 2");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(28.0, value, 0.0);
    }

    @Test
    public void addAndsubtract() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 - 2 + 4 + 34 - 2 - 10");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(54.0, value, 0.0);
    }

    @Test
    public void multiply() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("4 * 5");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(20.0, value, 0.0);
    }

    @Test
    public void divide() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(5.0, value, 0.0);
    }

    @Test
    public void multipleAndDivide() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6 * 4 / 10 * 3 * 4 / 8");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(3.0, value, 0.0);
    }

    @Test
    public void multipleDivideAddAndSubtract() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30 / 6 * 4 + 10 * 3 - 8 / 4");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(48.0, value, 0.0);
    }

    @Test
    public void decimals() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("30.123 / 6.553 * 4.212 + 10.1 * 3.12312 - 8.999 / 4.1231");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(48.7227615443, value, 0.0000000001);
    }



    @Test
    public void parentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1+2)*3");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(9.0, value, 0.0);
    }

    @Test
    public void multipleDepthParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("((6+2)+4)/6");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(2.0, value, 0.0);
    }

    @Test
    public void separateParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + 2) * (8 - 4) ");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(12.0, value, 0.0);
    }

    @Test
    public void multiplicationInParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("12/(2 * 3)");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(2.0, value, 0.0);
    }

    @Test
    public void complexParentheses() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1+19)*12/(((2 * 3) + (12/3) * 3)/4)");

        // Act
        Calculator calculator = new Calculator(null, new VariableRepository(), null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(53.33333, value, 0.001);
    }

    @Test
    public void variables() {
        // Arrange
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(1 + weeks) * reds");
        VariableRepository variableRepository = new VariableRepository();
        variableRepository.setVariableValue("weeks", 50);
        variableRepository.setVariableValue("reds", 2);


        // Act
        Calculator calculator = new Calculator(null, variableRepository, null);
        double value = calculator.resolveExpression(tokens);

        // Assert
        assertEquals(102.0, value, 0.0);
    }


}
