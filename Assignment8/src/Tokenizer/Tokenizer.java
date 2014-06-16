package Tokenizer;

import Tokenizer.Tokens.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class tokenizes arithmetic expressions.
 * @author Nathan Flint
 * @version Assignment 8: Recursion and Parsing
 */
public class Tokenizer {
    /**
     * Tokenizes an expression into a list of tokens in the order they were found in the expression.
     * @param expression an arithmetic expression
     * @return a list of tokens.
     */
    public List<Token> tokenize(String expression) {
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Values and variables are more than one character, so they are built a character
        // at a time, stored in this variable.
        String temp = "";

        // Loops through the expression character by character creating tokens.
        for (int i = 0; i < expression.length(); i++) {
            String currentChar = expression.charAt(i) + "";

            // If it's a value or variable, then add it to temp
            if (isValue(currentChar) || isVariable(currentChar)) {
                temp += currentChar;
                continue;
            }

            // If there something in the tempVarVal then add it as a token.
            if (temp.length() > 0) {
                // Checking to see if the temp val/var is actually a value or variable.
                if (isValue(temp) || isVariable(temp)) {
                    tokens.add(createToken(temp));
                }
                else {
                    // It's not a value or variable to add an unknown token
                    tokens.add(new UnknownToken(temp));
                }
                // clear the temp to get ready for the next one.
                temp = "";
            }

            // If the current char is an operator then add it as a token.
            if (isOperator(currentChar))
                tokens.add(createToken(currentChar));
            else if (currentChar.equals(" "))
                // Ignore spaces
                continue;
            else
                // Unrecognized operator.
                tokens.add(new UnknownToken(currentChar));
        }

        // Loop above doesn't handle the final token, if there is a last token, this handles it.
        if (temp.length() > 0) {
            Token trailingToken = createToken(temp);
            if (trailingToken != null)
                tokens.add(trailingToken);
            else
                tokens.add(new UnknownToken(temp));
        }

        return tokens;
    }

    /**
     * Creates a token given a string.
     * @param expression a string representation of a token
     * @return a token with specific sub type
     */
    public Token createToken(String expression) {
        if (isValue(expression))
            return new ValueToken(expression);

        if (isVariable(expression))
            return new VariableToken(expression);

        if (isAssignmentOperator(expression))
            return new AssignmentToken();

        if (isOpenParentheses(expression))
            return new OpenParenToken();

        if (isCloseParentheses(expression))
            return new CloseParenToken();

        if (isPlusOperator(expression))
            return new PlusToken();

        if (isMinusOperator(expression))
            return new MinusToken();

        if (isProductOperator(expression))
            return new ProductToken();

        if (isQuotientOperator(expression))
            return new QuotientToken();

        if (isListVariablesOperator(expression))
            return new ListVariablesToken();

        if (isRemoveVariableOperator(expression))
            return new RemoveVariableToken();

        return null;
    }

    /**
     * Identifies if the given string is a variable
     * @param expressionPiece part of an expression
     * @return true if the given expression is a variable
     */
    public boolean isVariable(String expressionPiece) {
        for (char character : expressionPiece.toCharArray()) {
            if (!Character.isAlphabetic(character))
                return false;
        }
        return true;
    }

    /**
     * Identifies if the given string is a value
     * @param expression
     * @return true if the given expression is a value
     */
    public boolean isValue(String expression) {
        // Keeps track of number of decimals in this number, only 1 or 0 allowed.
        int decimalCount = 0;

        // Check if all character in this expression are digitis or a decimal.
        for (char character : expression.toCharArray()) {
            if (!Character.isDigit(character)) {
                if (character != '.') {
                    return false;
                }
                else {
                    // found a decimal so increase the count
                    decimalCount++;
                }
            }
        }

        // if number as 2 or more decimals it is not a valid number.
        return decimalCount < 2;
    }

    public boolean isOpenParentheses(String expression) {
        return expression.equals("(");
    }

    public boolean isCloseParentheses(String expression) {
        return expression.equals(")");
    }

    public boolean isPlusOperator(String expression) {
        return expression.equals("+");
    }

    public boolean isMinusOperator(String expression) {
        return expression.equals("-");
    }

    public boolean isProductOperator(String expression) {
        return expression.equals("*");
    }

    public boolean isQuotientOperator(String expression) {
        return expression.equals("/");
    }

    public boolean isAssignmentOperator(String expression) {
        return expression.equals("=");
    }

    public boolean isListVariablesOperator(String expression) {
        return expression.equals("?");
    }

    public boolean isRemoveVariableOperator(String expression) {
        return expression.equals("!");
    }

    public boolean isOperator(String expression) {
        return isOpenParentheses(expression)
                || isCloseParentheses(expression)
                || isAssignmentOperator(expression)
                || isPlusOperator(expression)
                || isMinusOperator(expression)
                || isProductOperator(expression)
                || isQuotientOperator(expression)
                || isListVariablesOperator(expression)
                || isRemoveVariableOperator(expression);
    }


}
