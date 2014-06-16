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
        String tempVariableValue = "";

        // Loops through the expression character by character creating tokens.
        for (int i = 0; i < expression.length(); i++) {
            String currentChar = expression.charAt(i) + "";

            // If it's a value or variable, then add it to temp
            if (isValue(currentChar) || isVariable(currentChar)) {
                tempVariableValue += currentChar;
                continue;
            }

            // If there something in the tempVarVal then add it as a token.
            if (tempVariableValue.length() > 0) {
                // Checking to see if the temp val/var is actually a value or variable.
                if (isValue(tempVariableValue) || isVariable(tempVariableValue)) {
                    tokens.add(createToken(tempVariableValue));
                }
                else {
                    // It's not a value or variable to add an unknown token
                    tokens.add(new UnknownToken(tempVariableValue));
                }
                // clear the temp to get ready for the next one.
                tempVariableValue = "";
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
        if (tempVariableValue.length() > 0) {
            Token trailingToken = createToken(tempVariableValue);
            if (trailingToken != null)
                tokens.add(trailingToken);
            else
                tokens.add(new UnknownToken(tempVariableValue));
        }

        return tokens;
    }

    /**
     * Creates a token given a string.
     * @param expressionPiece a string representation of a token
     * @return a token with specific sub type
     */
    public Token createToken(String expressionPiece) {
        if (isValue(expressionPiece))
            return new ValueToken(expressionPiece);

        if (isVariable(expressionPiece))
            return new VariableToken(expressionPiece);

        if (isAssignmentOperator(expressionPiece))
            return new AssignmentToken();

        if (isOpenParentheses(expressionPiece))
            return new OpenParenToken();

        if (isCloseParentheses(expressionPiece))
            return new CloseParenToken();

        if (isPlusOperator(expressionPiece))
            return new PlusToken();

        if (isMinusOperator(expressionPiece))
            return new MinusToken();

        if (isProductOperator(expressionPiece))
            return new ProductToken();

        if (isQuotientOperator(expressionPiece))
            return new QuotientToken();

        if (isListVariablesOperator(expressionPiece))
            return new ListVariablesToken();

        if (isRemoveVariableOperator(expressionPiece))
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
     * @param expressionPiece
     * @return true if the given expression is a value
     */
    public boolean isValue(String expressionPiece) {
        // Keeps track of number of decimals in this number, only 1 or 0 allowed.
        int decimalCount = 0;

        // Check if all character in this expression are digitis or a decimal.
        for (char character : expressionPiece.toCharArray()) {
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

    public boolean isOpenParentheses(String expressionPiece) {
        return expressionPiece.equals("(");
    }

    public boolean isCloseParentheses(String expressionPiece) {
        return expressionPiece.equals(")");
    }

    public boolean isPlusOperator(String expressionPiece) {
        return expressionPiece.equals("+");
    }

    public boolean isMinusOperator(String expressionPiece) {
        return expressionPiece.equals("-");
    }

    public boolean isProductOperator(String expressionPiece) {
        return expressionPiece.equals("*");
    }

    public boolean isQuotientOperator(String expressionPiece) {
        return expressionPiece.equals("/");
    }

    public boolean isAssignmentOperator(String expressionPiece) {
        return expressionPiece.equals("=");
    }

    public boolean isListVariablesOperator(String expressionPiece) {
        return expressionPiece.equals("?");
    }

    public boolean isRemoveVariableOperator(String expressionPiece) {
        return expressionPiece.equals("!");
    }


    public boolean isOperator(String expressionPiece) {
        return isOpenParentheses(expressionPiece)
                || isCloseParentheses(expressionPiece)
                || isAssignmentOperator(expressionPiece)
                || isPlusOperator(expressionPiece)
                || isMinusOperator(expressionPiece)
                || isProductOperator(expressionPiece)
                || isQuotientOperator(expressionPiece)
                || isListVariablesOperator(expressionPiece)
                || isRemoveVariableOperator(expressionPiece);
    }


}
