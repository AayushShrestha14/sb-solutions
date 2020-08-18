package com.sb.solutions.api.customer.service;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.service.SecurityService;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
@Service
@Slf4j
public class CustomerInfoServiceImpl extends BaseServiceImpl<CustomerInfo, Long> implements
    CustomerInfoService {

    private static final String NULL_MESSAGE = "Invalid customer info id,Data does not exist";

    private final CustomerInfoRepository customerInfoRepository;


    private final SiteVisitService siteVisitService;
    private final SecurityService securityService;

    public CustomerInfoServiceImpl(
        @Autowired CustomerInfoRepository customerInfoRepository,
        SiteVisitService siteVisitService,
        SecurityService securityService) {
        super(customerInfoRepository);
        this.customerInfoRepository = customerInfoRepository;
        this.siteVisitService = siteVisitService;
        this.securityService = securityService;
    }


    @Override
    public CustomerInfo saveObject(Object o) {
        CustomerInfo customerInfo = new CustomerInfo();
        if (o instanceof Customer) {
            customerInfo = customerInfoRepository.findByAssociateId(((Customer) o).getId());
            log.info("Saving customer into customer info {}", o);
            if (ObjectUtils.isEmpty(customerInfo)) {
                customerInfo = new CustomerInfo();
            }
            customerInfo.setAssociateId(((Customer) o).getId());
            customerInfo.setCustomerType(CustomerType.INDIVIDUAL);
            customerInfo.setName(((Customer) o).getCustomerName());
            customerInfo.setIdType(CustomerIdType.CITIZENSHIP);
            customerInfo.setIdNumber(((Customer) o).getCitizenshipNumber());
            customerInfo.setIdRegPlace(((Customer) o).getCitizenshipIssuedPlace());
            customerInfo.setIdRegDate(((Customer) o).getCitizenshipIssuedDate());
            customerInfo.setContactNo(((Customer) o).getContactNumber());
            customerInfo.setEmail(((Customer) o).getEmail());
        }
        return this.save(customerInfo);
    }

    @Transactional
    @Override
    public CustomerInfo saveLoanInformation(Object o, Long customerInfoId, String template) {
        Optional<CustomerInfo> customerInfo = customerInfoRepository.findById(customerInfoId);
        Preconditions.checkArgument(customerInfo.isPresent(), NULL_MESSAGE);
        final CustomerInfo customerInfo1 = customerInfo.get();
        if ((template.equalsIgnoreCase(TemplateName.SITE_VISIT))) {

            final SiteVisit siteVisit = siteVisitService
                .save(objectMapper().convertValue(o, SiteVisit.class));
            customerInfo1.setSiteVisit(siteVisit);
        }
        if ((template.equalsIgnoreCase(TemplateName.SECURITY))) {

            final Security security = securityService
                .save(objectMapper().convertValue(o, Security.class));
            customerInfo1.setSecurity(security);
        }
        return customerInfoRepository.save(customerInfo1);
    }

    @Override
    public CustomerInfo findByAssociateId(Long id) {
        return customerInfoRepository.findByAssociateId(id);
    }

    @Override
    protected BaseSpecBuilder<CustomerInfo> getSpec(Map<String, String> filterParams) {
        filterParams.values().removeIf(Objects::isNull);
        return new CustomerInfoSpecBuilder(filterParams);
    }

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setDateFormat(new SimpleDateFormat(AppConstant.DATE_FORMAT));
        return objectMapper;
    }
}


class TemplateName {

    static final String SITE_VISIT = "SiteVisit";
    static final String FINANCIAL = "Financial";
    static final String SECURITY = "Security";


}


