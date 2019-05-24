package com.sb.solutions.core.enums;

/**
 * @author Sunil Babu Shrestha on 5/24/2019
 */
public enum DocStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    CLOSED("Closed");

    private final String value;

    DocStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}