package com.sb.solutions.core.enums;

/**
 * @author Sunil Babu Shrestha on 5/24/2019
 */
public enum LoanType {

    NEW_LOAN("New Loan"), RENEWED_LOAN("Renewed Loan"),
    CLOSURE_LOAN("'Closure Loan");

    private final String value;

    LoanType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
