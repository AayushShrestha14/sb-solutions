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

    private static Map<String, Object> productUtilsMap = new HashMap<>();


    @Value("${product.las}")
    public void setLas(Boolean las) {
        LAS = las;
        productUtilsMap.put("LAS", las);
    }

    @Value("${product.memo}")
    public void setMemo(Boolean memo) {
        MEMO = memo;
        productUtilsMap.put("MEMO", memo);
    }

    @Value("${product.account}")
    public void setAccount(Boolean account) {
        ACCOUNT = account;
        productUtilsMap.put("ACCOUNT", account);
    }

    @Value("${product.offerletter}")
    public void setOfferLetter(Boolean offerLetter) {
        OFFER_LETTER = offerLetter;
        productUtilsMap.put("OFFER_LETTER", offerLetter);
    }

    @Value("${product.eligibility}")
    public void setEligibility(Boolean eligibility) {
        ELIGIBILITY = eligibility;
        productUtilsMap.put("ELIGIBILITY", eligibility);
    }

    @Value("${product.dms}")
    public void setDms(Boolean dms) {
        DMS = dms;
        productUtilsMap.put("DMS", dms);
    }

    @Value("${product.nepTemplate}")
    public void setNepTemplate(Boolean nepTemplate) {
        NEP_TEMPLATE = nepTemplate;
        productUtilsMap.put("NEP_TEMPLATE", nepTemplate);
    }

    @Value("${product.approvalHierarchyForLoan}")
    public void setApprovalHierarchyForLoan(ApprovalType approvalType) {
        productUtilsMap.put("APPROVAL_HIERARCHY_LOAN", approvalType);
    }

    public static Map<String, Object> getProductUtilsMap() {
        return productUtilsMap;
    }
}
