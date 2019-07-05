package com.sb.solutions.core.enums;

/**
 * @author Rujan Maharjan on 6/12/2019
 */
public enum RoleType {

    MAKER("Maker"), APPROVAL("Approval");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
