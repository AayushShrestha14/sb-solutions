package com.sb.solutions.api.customerActivity.aop;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets.SetView;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.customerActivity.entity.CustomerActivity;
import com.sb.solutions.api.customerActivity.enums.ActivityType;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.service.CustomerLoanService;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.utils.string.StringUtil;

/**
 * @author : Rujan Maharjan on  9/22/2020
 **/
@Aspect
@Component
@Slf4j
public class CustomerLoanAspect {

    private static final String DESCRIPTION_UPDATE = "%s has been updated Successfully";
    private static final String DESCRIPTION_NEW = "New %s has been Saved Successfully";
    private final ActivityService activityService;
    private final CustomerLoanService customerLoanService;
    private final UserService userService;
    private ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setDateFormat(new SimpleDateFormat(AppConstant.DATE_FORMAT));

    public CustomerLoanAspect(
        ActivityService activityService,
        CustomerLoanService customerLoanService,
        UserService userService) {
        this.activityService = activityService;
        this.customerLoanService = customerLoanService;
        this.userService = userService;
    }

    private static List<Object> getDifference(Object s1, Object s2) throws IllegalAccessException {
        List<Object> values = new ArrayList<>();
        for (Field field : s1.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value1 = field.get(s1);
            Object value2 = field.get(s2);
            if (value1 != null && value2 != null) {
                if (!Objects.equals(value1, value2)) {
                    Map<String, Object> map = new HashMap<>();
                    String a = CaseFormat.UPPER_CAMEL
                        .to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                    map.put("fieldName", StringUtil
                        .getStringWithWhiteSpaceAndWithAllFirstLetterCapitalize(
                            a.replace("_", " ")));
                    map.put("prevValue", value1);
                    values.add(map);
                }
            }
        }
        return values;
    }

    private static SetView<?> getDifferenceBetweenSet(Set<?> newData,
        Set<?> oldData) {
        return com.google.common.collect.Sets.difference(oldData, newData);
    }

    @Pointcut("execution(* com.sb.solutions..*.*(..))")
    public void serviceLayer() {
    }

    /**
     * This is for  Loan Activity purpose only
     *
     * @param batch
     * @param customerLoanLog
     **/
    @Around(value = "serviceLayer() && @annotation(customerLoanLog) && args(batch,..)")
    public Object trackAroundCustomerLoan(ProceedingJoinPoint pjp, Object batch,
        CustomerLoanLog customerLoanLog)
        throws Throwable {
        CustomerLoan customerLoanPrev;
        String data = null;
        String description = null;
        CustomerActivity customerActivity = null;
        if (batch instanceof CustomerLoan) {
            CustomerLoan c = (CustomerLoan) batch;

            switch (customerLoanLog.value()) {
                case LOAN_UPDATE:
                    String loanName = c.getLoan().getName();
                    if (!ObjectUtils.isEmpty(c.getId())) {
                        Map<String, Object> diff = new LinkedHashMap<>();
                        customerLoanPrev = customerLoanService.findOne(c.getId());

                        loanName = customerLoanPrev.getLoan().getName();
                        ProposalData prevProposal = mapper
                            .readValue(customerLoanPrev.getProposal().getData(),
                                ProposalData.class);
                        ProposalData newProposal = mapper
                            .readValue(c.getProposal().getData(), ProposalData.class);

                        diff.put("proposal", getDifference(prevProposal, newProposal));
                        SetView<?> setView = getDifferenceBetweenSet(c.getTaggedGuarantors(),
                            customerLoanPrev.getTaggedGuarantors());
                        if (!setView.isEmpty()) {
                            diff.put("guarantor", customerLoanPrev.getTaggedGuarantors());
                        }
                        diff.values().removeIf(Objects::isNull);
                        diff.values()
                            .removeIf(value -> value.equals("null") || value.equals("undefined"));
                        data = mapper.writeValueAsString(diff);
                    }
                    description =
                        c.getId() == null ? String.format(DESCRIPTION_NEW, loanName)
                            : String.format(DESCRIPTION_UPDATE, loanName);
                    break;
                case LOAN_APPROVED:
                    data = mapper.writeValueAsString(c);
                    break;

            }
            customerActivity = CustomerActivity.builder()
                .modifiedOn(new Date())
                .modifiedBy(userService.getAuthenticatedUser())
                .data(data)
                .profile(c.getLoanHolder())
                .activity(customerLoanLog.value())
                .activityType(ActivityType.MANUAL)
                .customerLoanId(c.getId())
                .description(description)
                .build();
        }
        Object retVal = pjp.proceed();
        this.activityService.saveCustomerActivityByResponseSuccess(customerActivity, retVal);
        return retVal;


    }

    @Data
    static class ProposalData {

        private Long proposedLimit;
        private String premiumRateOnBaseRate;
        private String serviceCharge;
        private String tenureDurationInMonths;
        private String interestRate;
        private String baseRate;
        private String cibCharge;
        private String repaymentMode;
        private String purposeOfSubmission;
        private String disbursementCriteria;
        private String creditInformationReportStatus;
        private String incomeFromTheAccount;
        private String borrowerInformation;

        // Additional Fields--
        // for installment Amount--
        private String installmentAmount;
        // for moratoriumPeriod Amount--
        private String moratoriumPeriod;
        // for prepaymentCharge Amount--
        private String prepaymentCharge;
        // for prepaymentCharge Amount--
        private String purposeOfSubmissionSummary;
        // for commitmentFee Amount--
        private String commitmentFee;
        private String solConclusionRecommendation;
        private String waiverConclusionRecommendation;
        private String riskConclusionRecommendation;
    }
}
