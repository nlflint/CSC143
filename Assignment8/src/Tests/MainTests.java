package Tests;

import Calculator.Main;
import Tokenizer.Tokens.VariableRepository;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nate on 6/16/14.
 */
public class MainTests {
    @Test
    public void allAssignment8Tests() {
        // Arrange
        String[][] expressions = new String[][] {
                {"(1+2)*3","9.0"},
                {"weeks = 52","weeks = 52.0"},
                {"hoursPerWeek=40","hoursPerWeek = 40.0"},
                {"hourlyRate = 10.50","hourlyRate = 10.5"},
                {"bonus =1000","bonus = 1000.0"},
                {"annualSalary = weeks * hoursPerWeek * hourlyRate + bonus","annualSalary = 22840.0"},
                {"hoursPerWeek","40.0"},
                {"!weeks","‘weeks’ has been removed!"},
                {"pi = 3.14","pi = 3.14"},
                {"pi / 10","0.314"},
                {"?","hoursPerWeek = 40.0; hourlyRate = 10.5; bonus = 1000.0; annualSalary = 22840.0; pi = 3.14"},
                {"pi * 2 = 10","Expect a variable on the LHS."},
                {"(1 + 2","Missing closed parentheses."},
                {"(1 + 2))","Redundant closed parentheses."},
                {"area = pi * radius *","Unexpected end of line."},
                {"area = pi * radius","Variable ‘radius’ is undefined."},
                {"area = pi * radius^2","Unrecognizable token ‘^’."},
                {"area = 2ab","Unrecognizable token ‘2ab’."}};
        List<String[]> expressionsList = Arrays.asList(expressions);
        Main main = new Main();
        VariableRepository.deleteAllVariables();

        // Act & Assert
        for (String[] expression : expressionsList) {
            assertEquals(expression[1], main.resolveExpression(expression[0]));
        }
    }
}
