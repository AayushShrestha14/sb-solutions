package com.sb.solutions.core.exception;

public enum ResponseStatus {

    SUCCESS("Success", "S00"),


    FAILURE("Failure", "F00"),

    INTERNAL_SERVER_ERROR("Internal Server Error", "F02"),

    INVALID_SESSION("Session Invalid", "F03"),

    BAD_REQUEST("Bad Request", "F04"),

    VALIDATION_FAILED("Validation Fail", "F06"),

    UNAUTHORIZED_USER("Un-Authorized User", "F05");


    private String key;

    private String value;

    ResponseStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


}
