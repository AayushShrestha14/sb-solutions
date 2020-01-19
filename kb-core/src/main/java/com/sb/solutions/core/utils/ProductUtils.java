package com.sb.solutions.core.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author : Rujan Maharjan on  1/17/2020
 **/

public class ProductUtils {


    public static Boolean LAS;

    public static Boolean MEMO;

    public static Boolean ACCOUNT;

    public static Boolean OFFER_LETTER;

    public static Boolean ELIGIBILITY;

    public static Boolean DMS;


    @Value("${product.las}")
    public void setLas(Boolean las) {
        LAS = las;
    }

    @Value("${product.memo}")
    public void setMemo(Boolean memo) {
        MEMO = memo;
    }

    @Value("${product.account}")
    public void setAccount(Boolean account) {
        ACCOUNT = account;
    }

    @Value("${product.offerletter}")
    public void setOfferLetter(Boolean offerLetter) {
        OFFER_LETTER = offerLetter;
    }

    @Value("${product.eligibility}")
    public void setEligibility(Boolean eligibility) {
        ELIGIBILITY = eligibility;
    }

    @Value("${product.dms}")
    public void setDms(Boolean dms) {
        DMS = dms;
    }
}
