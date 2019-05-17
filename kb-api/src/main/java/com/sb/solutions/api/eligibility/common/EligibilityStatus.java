package com.sb.solutions.api.eligibility.common;

public enum  EligibilityStatus {
    NEW_REQUEST("New Request"), ELIGIBLE("Eligible"), NOT_ELIGIBLE("Not Eligible")
    , UNDER_REVIEW("Under Review");

    private final String value;

    EligibilityStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
