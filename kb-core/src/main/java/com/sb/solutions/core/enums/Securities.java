package com.sb.solutions.core.enums;

public enum Securities {
    LAND_SECURITY("Land Security"),

    BUILDING_SECURITY("Building Security"),

    VEHICLE_SECURITY("Vehicle Security"),

    PROPERTY_AND_MACHINERY_SECURITY("Property and Machinery Security");

    private final String value;

    Securities(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
