package com.sb.solutions.api.loan.repository.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.gson.Gson;
import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;

/**
 * @author Rujan Maharjan on 6/8/2019
 */
public class CustomerLoanSpec implements Specification<CustomerLoan> {

    private static final String FILTER_BY_LOAN = "loanConfigId";
    private static final String FILTER_BY_DOC_STATUS = "documentStatus";
    private static final String FILTER_BY_CURRENT_USER_ROLE = "currentUserRole";
    private static final String FILTER_BY_TO_USER = "toUser";
    private static final String FILTER_BY_BRANCH = "branchIds";
    private static final String FILTER_BY_CURRENT_STAGE_DATE = "currentStageDate";
    private static final String FILTER_BY_TYPE = "loanNewRenew";
    private static final String FILTER_BY_NOTIFY = "notify";
    private static final String FILTER_BY_CUSTOMER_NAME = "customerName";
    private static final String FILTER_BY_CURRENT_OFFER_LETTER_STAGE = "currentOfferLetterStage";
    private static final String FILTER_BY_COMPANY_NAME = "companyName";
    private static final String FILTER_BY_DOC_ACTION = "docAction";
    private static final String FILTER_BY_CUSTOMER_RELATIVE_NAME = "customerRelativeName";
    private static final String FILTER_BY_CUSTOMER_CITIZENSHIP = "citizenshipNumber";
    private static final String FILTER_BY_CUSTOMER_CITIZEN_ISSUE_DATE = "citizenshipIssuedDate";

    private static final String FILTER_BY_GUARANTOR_NAME = "guarantorName";
    private static final String FILTER_BY_GUARANTOR_CITIZENSHIP = "guarantorCitizenshipNumber";
    private static final String FILTER_BY_GUARANTOR_CITIZEN_ISSUE_DATE = "guarantorCitizenshipIssuedDate";
    private static final String FILTER_BY_GUARANTOR_DISTRICT_ID = "guarantorDistrictId";
    private static final String FILTER_BY_GUARANTOR_PROVINCE_ID = "guarantorProvinceId";

    private static final String FILTER_BY_SHARE_LOAN_EXCEEDING_LIMIT = "showShareLoanExcessingLimit";
    private static final String FILTER_BY_INSURANCE_EXPIRY = "isInsuranceExpired";
    private static final String FILTER_BY_HAS_INSURANCE = "hasInsurance";
    private static final String FILTER_BY_INSURANCE_NOTIFIED ="isInsuranceNotified";

    private final String property;
    private final String value;

    public CustomerLoanSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<CustomerLoan> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {

        switch (property) {

            case FILTER_BY_DOC_STATUS:
                if (value.equalsIgnoreCase("initial")) {
                    List<DocStatus> list = new ArrayList<>();
                    list.add(DocStatus.DISCUSSION);
                    list.add(DocStatus.DOCUMENTATION);
                    list.add(DocStatus.UNDER_REVIEW);
                    list.add(DocStatus.VALUATION);
                    Expression<String> exp = root.get(property);
                    Predicate predicate = exp.in(list);
                    return criteriaBuilder.and(predicate);
                } else {
                    return criteriaBuilder.equal(root.get(property), DocStatus.valueOf(value));
                }

            case FILTER_BY_LOAN:
                return criteriaBuilder
                    .and(criteriaBuilder.equal(root.join("loan").get("id"), Long.valueOf(value)));

            case FILTER_BY_CURRENT_USER_ROLE:
                return criteriaBuilder
                    .equal(root.join("currentStage", JoinType.LEFT).join("toRole").get("id"),
                        Long.valueOf(value));

            case FILTER_BY_BRANCH:
                Pattern pattern = Pattern.compile(",");
                List<Long> list = pattern.splitAsStream(value)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
                Expression<String> exp = root.join("branch").get("id");
                Predicate predicate = exp.in(list);
                return criteriaBuilder.and(predicate);

            case FILTER_BY_CURRENT_STAGE_DATE:
                Gson gson = new Gson();
                Map dates = gson.fromJson(value, Map.class);
                try {
                    return criteriaBuilder.between(root.get("createdAt"),
                        new SimpleDateFormat("MM/dd/yyyy")
                            .parse(String.valueOf(dates.get("startDate"))),
                        new SimpleDateFormat("MM/dd/yyyy")
                            .parse(String.valueOf(dates.get("endDate"))));
                } catch (ParseException e) {
                    return null;
                }

            case FILTER_BY_TO_USER:
                return criteriaBuilder
                    .equal(
                        root.join("currentStage", JoinType.LEFT).join(FILTER_BY_TO_USER).get("id"),
                        Long.valueOf(value));

            case FILTER_BY_TYPE:
                return criteriaBuilder.equal(root.get("loanType"), LoanType.valueOf(value));

            case FILTER_BY_NOTIFY:
                Predicate notifyPredicate = criteriaBuilder
                    .equal(root.get(FILTER_BY_NOTIFY), Boolean.valueOf(value));
                Predicate notedByPredicate = criteriaBuilder.isNull(root.get("notedBy"));
                return criteriaBuilder.and(notifyPredicate, notedByPredicate);

            case FILTER_BY_CUSTOMER_NAME:
                return criteriaBuilder
                    .like(criteriaBuilder
                            .lower(root.join("customerInfo").get(FILTER_BY_CUSTOMER_NAME)),
                        value.toLowerCase() + "%");

            case FILTER_BY_COMPANY_NAME:
                return criteriaBuilder
                    .like(criteriaBuilder
                            .lower(root.join("companyInfo").get(FILTER_BY_COMPANY_NAME)),
                        "%" + value.toLowerCase() + "%");

            case FILTER_BY_DOC_ACTION:
                return criteriaBuilder
                    .and(criteriaBuilder.equal(root.get("currentStage").get("docAction"),
                        DocAction.valueOf(value)));

//
//            case FILTER_BY_CURRENT_OFFER_LETTER_STAGE:
//
//                Predicate currentOfferLetterStage = criteriaBuilder
//                    .equal(
//                        root.join("customerOfferLetter", JoinType.LEFT)
//                            .join("offerLetterStage", JoinType.LEFT)
//                            .join("toUser", JoinType.LEFT).get("id"),
//                        Long.valueOf(value));
//                Predicate noCurrentOfferLetterStage = criteriaBuilder
//                    .isNull(root.get("customerOfferLetter"));
//                Predicate approvedOfferLetter = criteriaBuilder
//                    .equal(
//                        root.join("customerOfferLetter", JoinType.LEFT)
//                            .get("docStatus"),
//                        DocStatus.valueOf(DocStatus.APPROVED.name()));
//                return criteriaBuilder
//                    .or(noCurrentOfferLetterStage, currentOfferLetterStage,approvedOfferLetter);

            case FILTER_BY_CUSTOMER_RELATIVE_NAME:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(root.join("customerInfo").join("customerRelatives").get(property),
                            value));

            case FILTER_BY_CUSTOMER_CITIZENSHIP:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(root.join("customerInfo").join("customerRelatives").get(property),
                            value));

            case FILTER_BY_CUSTOMER_CITIZEN_ISSUE_DATE:
                try {
                    return criteriaBuilder
                        .and(criteriaBuilder
                            .equal(
                                root.join("customerInfo").join("customerRelatives").get(property),
                                new SimpleDateFormat("yyyy-MM-dd").parse(value)));
                } catch (ParseException e) {
                    return null;
                }

            case FILTER_BY_GUARANTOR_NAME:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(root.join("guarantor").join("guarantorList").get("name"),
                            value));

            case FILTER_BY_GUARANTOR_CITIZENSHIP:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(root.join("guarantor").join("guarantorList").get("citizenNumber"),
                            value));

            case FILTER_BY_GUARANTOR_CITIZEN_ISSUE_DATE:
                try {
                    return criteriaBuilder
                        .and(criteriaBuilder
                            .equal(
                                root.join("guarantor").join("guarantorList").get("issuedYear"),
                                new SimpleDateFormat("yyyy-MM-dd").parse(value)));
                } catch (ParseException e) {
                    return null;
                }

            case FILTER_BY_GUARANTOR_DISTRICT_ID:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(
                            root.join("guarantor").join("guarantorList").join("district").get("id"),
                            value));

            case FILTER_BY_GUARANTOR_PROVINCE_ID:
                return criteriaBuilder
                    .and(criteriaBuilder
                        .equal(
                            root.join("guarantor").join("guarantorList").join("province").get("id"),
                            value));

            case FILTER_BY_SHARE_LOAN_EXCEEDING_LIMIT:
                Predicate predicateForLimitExceed = criteriaBuilder
                    .and(criteriaBuilder
                        .equal(root.get("limitExceed"),
                            1));
                Predicate predicateForShareTemplate = criteriaBuilder.
                    isMember(AppConstant.TEMPLATE_SHARE_SECURITY,
                        root.join("loan").get("templateList"));
                return criteriaBuilder.and(predicateForLimitExceed, predicateForShareTemplate);

            case FILTER_BY_INSURANCE_EXPIRY:
                return criteriaBuilder.equal(root.get(FILTER_BY_INSURANCE_EXPIRY), true);

            case FILTER_BY_INSURANCE_NOTIFIED:
                return criteriaBuilder.equal(root.get(FILTER_BY_INSURANCE_NOTIFIED),Boolean.parseBoolean(value));

            case FILTER_BY_HAS_INSURANCE:
                return criteriaBuilder.isNotNull(root.get("insurance"));


            default:
                return null;
        }
    }


}
