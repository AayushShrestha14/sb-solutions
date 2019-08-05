package com.sb.solutions.core.enums;

/**
 * constant list of possible income source of Customer
 *
 * @author Sunil Babu Shrestha on 8/4/2019
 */
public enum IncomeSource {
    SALARY("Salary"),
    RENT("Rent"),
    REMITTANCE("Remittance"),
    VEHICLE("Vehicle Income"),
    BUSINESS("Business"),
    PROFIT("Business Profit"),
    PENSION("Pension"),
    CAPITAL_GAIN("Capital Gain Income"),
    AGRICULTURE("Agriculture");
    private String income;

    IncomeSource(String income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return this.income;
    }
}
