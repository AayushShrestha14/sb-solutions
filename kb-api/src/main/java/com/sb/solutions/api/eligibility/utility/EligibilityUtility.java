package com.sb.solutions.api.eligibility.utility;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EligibilityUtility {

    public static final double evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer strBuff = new StringBuffer();
                while(i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.'))
                    strBuff.append(tokens[i++]);
                values.push(Double.parseDouble(strBuff.toString()));
            } else if (tokens[i] == '(')
                operators.push(tokens[i]);
            else if (tokens[i] == ')') {
                while(operators.peek() != '(') {
                    values.push(doOperations(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.empty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(doOperations(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
            }
        }
        while (!operators.empty()) {
            values.push(doOperations(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    private static double doOperations(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Can not divide by zero");
                return a / b;
            default:
                return 0;
        }
    }

    private static boolean hasPrecedence(char firstOperand, char secondOperand) {
        if (secondOperand == '(' || secondOperand == ')')
            return false;
        if ((firstOperand == '*' || firstOperand == '/') && (secondOperand == '+' || secondOperand == '-'))
            return false;
        else
            return true;
    }

    public static final Map<String, Long> extractOperands(String expression,
                                                     List<EligibilityQuestion> eligibilityQuestions) {
        final char[] characters = expression.toCharArray();
        final Map<String, Long> operands = new HashMap<>();
        for (char ch: characters) {
            if (ch >= 'A' && ch <= 'Z') {
                eligibilityQuestions.stream()
                        .filter(eligibilityQuestion -> eligibilityQuestion.getOperandCharacter().equals(String.valueOf(ch)))
                        .findAny().map(eligibilityQuestion -> operands.put(String.valueOf(ch), eligibilityQuestion.getId()));
            }
        }
        return operands;
    }
}
