package com.sb.solutions.core.enums;

public enum AccountStatus {
    NEW_REQUESTED("New Request"), APPROVAL("Approval"), REJECTED("Rejected");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
