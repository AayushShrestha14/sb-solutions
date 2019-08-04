package com.sb.solutions.core.enums;

/**
 * Constant List of possible occupation of customer
 *
 * @author Sunil Babu Shrestha on 8/4/2019
 */
public enum Occupation {

    JOB_HOLDER("Job Holder"),
    HOUSEWIFE("Housewife"),
    FOREIGN_EMPLOYMENT("Foreign Employment"),
    BUSINESS("Business"),
    PENSIONER("Pensioner"),
    FARMER("Farmer"),
    MARKETABLE_SECURITIES_TRADER("Marketable Securities Trader"),
    Freelancer("Freelancer");

    private String occupation;

    Occupation(String s) {
        this.occupation = s;
    }

    @Override
    public String toString() {
        return this.occupation;
    }}
