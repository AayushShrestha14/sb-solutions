package com.sb.solutions.api.clientInfo.enums;

public enum Securities {
    LAND_SECURITY("land security"),
    APARTMENT_SECURITY("apartment security"),
    BOTH_TYPE("both");
    public final String value;

    Securities(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
