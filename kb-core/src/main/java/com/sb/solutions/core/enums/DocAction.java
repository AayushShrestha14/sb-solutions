package com.sb.solutions.core.enums;

/**
 * @author Sunil Babu Shrestha on 5/24/2019
 * <p>
 * Collections of Action that can be performed on Loan Document
 */
public enum DocAction {
    INITIATE("Initiate"),
    FORWARD("Forward"),
    BACKWARD("Backward"),
    APPROVED("Approved"),
    REJECT("Reject"),
    TRANSFER("Transfer");

    private final String value;

    DocAction(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

}
