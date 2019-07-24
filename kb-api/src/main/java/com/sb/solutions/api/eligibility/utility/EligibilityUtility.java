package com.sb.solutions.api.eligibility.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;

public class EligibilityUtility {

    public static String convertToMockFormula(String formula) {
        char[] mockFormulaChar = formula.toCharArray();
        int i = -1;
        for (char ch : mockFormulaChar) {
            i++;
            if (Character.isLetter(ch)) {
                mockFormulaChar[i] = '2';
            }
        }
        return new String(mockFormulaChar);
    }

    public static Map<String, Long> extractOperands(String expression,
        List<EligibilityQuestion> eligibilityQuestions) {
        final char[] characters = expression.toCharArray();
        final Map<String, Long> operands = new HashMap<>();
        for (char ch : characters) {
            if (Character.isLetter(ch)) {
                eligibilityQuestions.stream()
                    .filter(eligibilityQuestion -> eligibilityQuestion.getOperandCharacter()
                        .equals(String.valueOf(ch)))
                    .findAny().map(eligibilityQuestion -> operands
                    .put(String.valueOf(ch), eligibilityQuestion.getId()));
            }
        }
        return operands;
    }
}
