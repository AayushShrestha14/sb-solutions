package com.sb.solutions.api.customerActivity.aop;

/**
 * @author : Rujan Maharjan on  9/16/2020
 **/
public enum Activity {
    CUSTOMER_UPDATE("Customer Update"),
    INSURANCE_UPDATE("Insurance Update"),
    SITE_VISIT_UPDATE("Site Visit Update"),
    GUARANTOR_UPDATE("Guarantor Update"),
    FINANCIAL_UPDATE("Financial Update"),
    CRG_UPDATE("CRG Update"),
    SECURITY("Security Update"),
    SHARE_SECURITY("Share Security Update"),
    CUSTOMER_GROUP_UPDATE("Customer Group Update"),
    LOAN_APPROVED("Loan Approved");


    private final String value;

    Activity(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }


}
