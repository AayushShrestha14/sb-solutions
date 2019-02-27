package com.sb.solutions.api.approvallimit.emuns;

public enum LoanApprovalType {

    PERSONAL_TYPE("personal"), BUSINESS_TYPE("business"), BOTH_TYPE("both");

    private final String value;

    LoanApprovalType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
