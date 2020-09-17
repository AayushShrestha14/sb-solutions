package com.sb.solutions.core.enums;

/**
 * Constant List of possible Business Type of the Company
 */
public enum BusinessType {

    PRODUCTION("Production"),
    TRADING("Trading"),
    SERVICE_PROVIDER("Service Provider"),
    CONSULTANCY("Consultancy"),
    CONSTRUCTION_SERVICE("Construction Service"),
    AGRICULTURE("Agriculture"),
    OTHER("Other");

    private String businessType;

    BusinessType(String s) {
        this.businessType = s;
    }

    @Override
    public String toString() {
        return this.businessType;
    }

}
