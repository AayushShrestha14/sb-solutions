package com.sb.solutions.api.customerActivity.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customer.service.CustomerInfoService;
import com.sb.solutions.api.customerActivity.entity.CustomerActivity;

/**
 * @author : Rujan Maharjan on  9/16/2020
 **/
@Aspect
@Component
@Slf4j
public class CustomerAspect {

    static final String SITE_VISIT = "SiteVisit";
    static final String FINANCIAL = "Financial";
    static final String SECURITY = "Security";
    static final String SHARE_SECURITY = "Share Security";
    static final String GUARANTOR = "Guarantor";
    static final String INSURANCE = "Insurance";
    static final String CUSTOMER_GROUP = "customerGroup";
    static final String CRG_ALPHA = "CrgAlpha";
    static final String CRG = "Crg";


    private CustomerInfoService customerInfoService;


    @Autowired
    public CustomerAspect(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    private Activity activity(String template) {
        switch (template) {
            case INSURANCE:
                return Activity.INSURANCE_UPDATE;
            case SITE_VISIT:
                return Activity.SITE_VISIT_UPDATE;
            default:
                return Activity.CUSTOMER_UPDATE;
        }
    }

//    @AfterReturning(value = "serviceLayer()  && @annotation(customerActivityLog) && args(batch,id,template,..)", returning = "retVal")
//    public void trackAfterWithId(Object retVal, CustomerActivityLog customerActivityLog,
//        Object batch, Object id, String template) {
//        log.info("intial customerInfo",this.prevValue);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        ObjectMapper objectMapper = new ObjectMapper();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            return;
//        }
//        Map<String, Object> map = objectMapper
//            .convertValue(authentication.getPrincipal(), Map.class);
//        System.out.println(customerActivityLog.value());
//
//        log.info("trackAfterwithId{} and of customer id{} with template{} activity{} ", batch, id,
//            template, activity(template));
//        CustomerInfo returnValue = (CustomerInfo) retVal;
//        Profile profile = new Profile();
//        BeanUtils.copyProperties(returnValue, profile);
//        profile.setId(returnValue.getId());
//        log.info("returning val{}", returnValue);
//        log.info("profile val{}", profile);
//        return;
//    }

    @AfterThrowing(value = "serviceLayer() && @annotation(customerActivityLog)", throwing = "ex")
    public void doRecoveryActions(CustomerActivityLog customerActivityLog, Exception ex) {
        log.info("check doRecoveryActions customerActionTrack{} and Exception{}",
            customerActivityLog.value());
    }

//    @Before(value = "serviceLayer() && @annotation(customerActionTrack) && args(batch,id,..)")
//    public void trackBefore(Object batch, CustomerActivityLog customerActionTrack, Long id) {
//        PrevValue prevValue = new PrevValue();
//        prevValue.setCustomerInfoPrev(customerInfoService.findOne(id).get());
//        this.prevValue = prevValue;
//
//        log.info("trackBefore{} and map{}", batch, customerActionTrack.value());
//        return;
//    }

    @Around(value = "serviceLayer() && @annotation(customerActionTrack) && args(batch,id,..)")
    public Object trackAround(ProceedingJoinPoint pjp, Object batch,
        CustomerActivityLog customerActionTrack, Long id)
        throws Throwable {
        CustomerActivity customerActivity = new CustomerActivity();
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerInfo customerInfoPrev = customerInfoService.findOne(id).get();
        log.info("old Value{}  and map{}", customerInfoPrev, customerActionTrack.value());
        Profile p = new Profile(customerInfoPrev.getId(), customerInfoPrev.getName(),
            customerInfoPrev.getCustomerType());
        String pToString = objectMapper.writeValueAsString(p);
        customerActivity.setProfile(pToString);
        customerActivity.setData(objectMapper.writeValueAsString(batch));
        customerActivity.setActivity(customerActionTrack.value());

        Object retVal = pjp.proceed();
        log.info("---------------------------------------------------------");
        log.info("map{} and new Value {}", customerActionTrack.value(), retVal);
        // stop stopwatch
        return retVal;


    }

    @Pointcut("execution(* com.sb.solutions..*.*(..))")
    public void serviceLayer() {
    }

    @Data
    @Builder
    class Profile {

        Long id;
        String name;
        CustomerType customerType;
    }


}
