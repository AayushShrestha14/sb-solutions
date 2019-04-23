package com.sb.solutions.api.approvallimit.emuns;

public enum LoanCategory {

    WORKING_CAPITAL_LOAN("Working Capital Loan"),
    BRIDGE_GAP_LOAN("Bridge Gap Loan"),
    TERM_LOAN("Term Loan"),
    SHARE_LOAN("Share Loan"),
    HIRE_PURCHASE_LOAN("Hire Purchase Loan"),
    PERSONAL_LOAN("Personal Loan"),
    DEPRIVED_LOAN("Deprived Loan"),
    HOME_LOAN("Home Loan"),
    LOAN_AGAINST_BOND("Loan Against Bond"),
    BIKE_HP_LOAN("Bike HP Loan"),
    FIXED_DEPOSIT_LOAN("Fixed Deposit Loan"),
    CONSUMER_FINANCE_LOAN("Consumer Finance Loan"),
    MEMO("Memo");

    private final String value;

    private LoanCategory(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
