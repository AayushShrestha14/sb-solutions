package com.sb.solutions.api.customerActivity.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * @author : Rujan Maharjan on  9/22/2020
 **/
@Aspect
@Component
@Slf4j
public class CustomerLoanAspect {

    private final ActivityService activityService;
    private final CustomerLoanService customerLoanService;
    private final UserService userService;
    private static final String DESCRIPTION_UPDATE = "%s has been updated Successfully";
    private static final String DESCRIPTION_NEW = "New %s has been Saved Successfully";

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
        CustomerLoan customerLoanPrev = new CustomerLoan();
        String data = null;
        String description = null;
        CustomerActivity customerActivity = null;
        if (batch instanceof CustomerLoan) {
            CustomerLoan c = (CustomerLoan) batch;

            switch (customerLoanLog.value()) {
                case LOAN_UPDATE:
                    String loanName = c.getLoan().getName();
                    if (!ObjectUtils.isEmpty(c.getId())) {
                        customerLoanPrev = customerLoanService.findOne(c.getId());
                        loanName = customerLoanPrev.getLoan().getName();
                        data = mapper.writeValueAsString(customerLoanPrev);
                    }
                    description =
                        data == null ? String.format(DESCRIPTION_NEW, loanName)
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

}
