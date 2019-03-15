package com.sb.solutions.core.enums;

public enum Stage {
    UNDER_REVIEW("Under Review"), APPROVED("Approved"), REJECTED("Rejected"),
    FORWARD("Forward"), BACKWARD("Backward"), RECEIVED("Received"), SENT("Sent");

    private final String value;

    Stage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Review{" +
            "value='" + value + '\'' +
            '}';
    }
}
