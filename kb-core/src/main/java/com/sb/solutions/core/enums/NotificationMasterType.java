package com.sb.solutions.core.enums;


/**
 * @author Aashish shrestha on 12/Mar/2020
 */

public enum NotificationMasterType {

    INSURANCE_EXPIRY_NOTIFY("INSURANCE_EXPIRY_NOTIFY");

    private String value;

    NotificationMasterType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
