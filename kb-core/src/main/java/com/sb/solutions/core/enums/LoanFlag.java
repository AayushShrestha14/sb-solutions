package com.sb.solutions.core.enums;

/**
 * Enum array values [0] defines the flag precedence for an alert.
 * Enum array values [1] defines the flag description.
 *
 * @author Elvin Shrestha on 4/22/2020
 */
public enum LoanFlag {
    ZERO_PROPOSAL_AMOUNT("1", "Cannot forward loan as proposed amount is zero"),
    INSURANCE_EXPIRY("2", "Insurance expiry date is about to meet."),
    INSUFFICIENT_SHARE_AMOUNT("3",
        "Insufficient Security considered value. Maximum considered amount is %s");

    private final String[] value;

    LoanFlag(String... value) {
        this.value = value;
    }

    public String[] getValue() {
        return value;
    }
}
