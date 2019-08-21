package com.sb.solutions.api.eligibility.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sb.solutions.api.eligibility.question.entity.EligibilityQuestion;
import com.sb.solutions.core.enums.Status;

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

    public static Map<String, String> extractOperands(String expression,
        List<EligibilityQuestion> eligibilityQuestions) {
        final char[] characters = expression.toCharArray();
        final Map<String, String> operands = new HashMap<>();
        for (char ch : characters) {
            if (Character.isLetter(ch)) {
                eligibilityQuestions.stream()
                    .filter(eligibilityQuestion -> eligibilityQuestion.getOperandCharacter()
                        .equals(String.valueOf(ch))
                        && eligibilityQuestion.getStatus() == Status.ACTIVE)
                    .findAny().map(eligibilityQuestion -> operands
                    .put(String.valueOf(ch), String.valueOf(eligibilityQuestion.getId())));
                if (ch == 'I') {
                    operands.put("I", "reserved");
                }
            }
        }
        return operands;
    }
}
