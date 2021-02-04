package com.sb.solutions.core.enums;

public enum LoanTag {
    GENERAL("GENERAL"),
    VEHICLE("VEHICLE"),
    FIXED_DEPOSIT("FIXED DEPOSIT"),
    SHARE_SECURITY("SHARE SECURITY");

    private final String value;

    LoanTag(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
