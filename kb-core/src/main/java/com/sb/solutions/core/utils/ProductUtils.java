package com.sb.solutions.core.utils;

import java.util.HashMap;
import java.util.Map;

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

    public static Boolean NEP_TEMPLATE;

    private Map<String, Boolean> productUtilsMap;


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

    @Value("${product.nepTemplate}")
    public void setNepTemplate(Boolean nepTemplate) {
        NEP_TEMPLATE = nepTemplate;
    }

    public static Map<String, Boolean> getProductUtilsMap() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("LAS", LAS);
        map.put("MEMO", MEMO);
        map.put("ELIGIBILITY", ELIGIBILITY);
        map.put("ACCOUNT", ACCOUNT);
        map.put("NEP_TEMPLATE", NEP_TEMPLATE);
        map.put("OFFER_LETTER", OFFER_LETTER);
        return map;
    }
}
