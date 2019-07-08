package com.sb.solutions.core.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Sunil Babu Shrestha on 7/8/2019
 */
public final class ExpressionParserUtil {

    private ExpressionParserUtil() {
    }

    /**
     * @param expressionString
     * @return
     * @throws SpelParseException Caller method need to handle this exception,
     * this exception occur if there is no balanced parentheses in an expression for example unbalanced braces
     */
    public static Double arithematicParse(String expressionString) throws SpelParseException {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expressionString);
        return expression.getValue(Double.class);
    }

    // used for test
    public static void main(String[] args) {
        String expressionString = "((12*5)+2)/2";
        System.out.println(arithematicParse(expressionString));
    }
}
