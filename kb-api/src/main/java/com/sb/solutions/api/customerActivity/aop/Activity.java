package com.sb.solutions.api.customerActivity.aop;

/**
 * @author : Rujan Maharjan on  9/16/2020
 **/
public enum Activity {
    CUSTOMER_UPDATE("Customer Update"),
    INSURANCE_UPDATE("Insurance Update"),
    SITE_VISIT_UPDATE("Site Visit Update"),
    GUARANTOR_UPDATE("Guarantor Update"),
    FINANCIAL_UPDATE("Site Visit Update"),
    CRG_UPDATE("Site Visit Update"),
    SECURITY("Security Update"),
    SHARE_SECURITY("Share Security Update");


    private final String value;

    Activity(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }


}
