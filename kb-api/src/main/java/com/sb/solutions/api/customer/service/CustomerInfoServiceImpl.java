package com.sb.solutions.api.customer.service;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.companyInfo.model.repository.CompanyInfoRepository;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.financial.entity.Financial;
import com.sb.solutions.api.financial.service.FinancialService;
import com.sb.solutions.api.guarantor.entity.GuarantorDetail;
import com.sb.solutions.api.guarantor.service.GuarantorDetailService;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.service.InsuranceService;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.service.SecurityService;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.service.ShareSecurityService;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.RoleType;
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
    private final FinancialService financialService;
    private final SecurityService securityService;
    private final ShareSecurityService shareSecurityService;
    private final GuarantorDetailService guarantorDetailService;
    private final UserService userService;
    private final InsuranceService insuranceService;

    public CustomerInfoServiceImpl(
        @Autowired CustomerInfoRepository customerInfoRepository,
        FinancialService financialService,
        SiteVisitService siteVisitService,
        SecurityService securityService,
        ShareSecurityService shareSecurityService,
        GuarantorDetailService guarantorDetailService,
        UserService userService,
        InsuranceService insuranceService) {
        super(customerInfoRepository);
        this.customerInfoRepository = customerInfoRepository;
        this.financialService = financialService;
        this.siteVisitService = siteVisitService;
        this.userService = userService;
        this.securityService = securityService;
        this.shareSecurityService = shareSecurityService;
        this.guarantorDetailService = guarantorDetailService;
        this.insuranceService = insuranceService;
    }


    @Override
    public CustomerInfo saveObject(Object o) {
        CustomerInfo customerInfo = new CustomerInfo();
        User user = userService.getAuthenticatedUser();
        Preconditions.checkArgument(user.getRole().getRoleType() == RoleType.MAKER,
            "You are not Authorize to save customer info");
        if (o instanceof Customer) {
            customerInfo = customerInfoRepository
                .findByAssociateIdAndCustomerType(((Customer) o).getId(), CustomerType.INDIVIDUAL);
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
        if (o instanceof CompanyInfo) {
            customerInfo = customerInfoRepository
                .findByAssociateIdAndCustomerType(((CompanyInfo) o).getId(), CustomerType.COMPANY);
            log.info("Saving company into customer info {}", o);
            if (ObjectUtils.isEmpty(customerInfo)) {
                customerInfo = new CustomerInfo();
            }
            customerInfo.setAssociateId(((CompanyInfo) o).getId());
            customerInfo.setCustomerType(CustomerType.COMPANY);
            customerInfo.setName(((CompanyInfo) o).getCompanyName());
            customerInfo.setIdType(CustomerIdType.PAN);
            customerInfo.setIdNumber(((CompanyInfo) o).getPanNumber());
        }
        customerInfo.setBranch(user.getBranch().get(0));
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
        else if ((template.equalsIgnoreCase(TemplateName.FINANCIAL))) {

            final Financial financial = financialService
                .save(objectMapper().convertValue(o, Financial.class));
            customerInfo1.setFinancial(financial);
        }
        else if ((template.equalsIgnoreCase(TemplateName.SECURITY))) {

            final Security security = securityService
                .save(objectMapper().convertValue(o, Security.class));
            customerInfo1.setSecurity(security);
        }
        else if ((template.equalsIgnoreCase(TemplateName.SHARE_SECURITY))) {

            final ShareSecurity shareSecurity = shareSecurityService
                .save(objectMapper().convertValue(o, ShareSecurity.class));
            customerInfo1.setShareSecurity(shareSecurity);
        }
        else if ((template.equalsIgnoreCase(TemplateName.GUARANTOR))) {
            final GuarantorDetail guarantors = guarantorDetailService
                .save(objectMapper().convertValue(o, GuarantorDetail.class));
            customerInfo1.setGuarantors(guarantors);
        }
        else if ((template.equalsIgnoreCase(TemplateName.INSURANCE))) {
            final Insurance insurance = insuranceService
                .save(objectMapper().convertValue(o, Insurance.class));
            customerInfo1.setInsurance(insurance);
        }
        return customerInfoRepository.save(customerInfo1);
    }

    @Override
    public CustomerInfo findByAssociateIdAndCustomerType(Long id, CustomerType customerType) {
        return customerInfoRepository.findByAssociateIdAndCustomerType(id, customerType);
    }

    @Override
    protected BaseSpecBuilder<CustomerInfo> getSpec(Map<String, String> filterParams) {
        filterParams.values().removeIf(Objects::isNull);
        filterParams.values().removeIf(value -> value.equals("null") || value.equals("undefined"));
        String branchAccess = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        if (filterParams.containsKey("branchIds")) {
            branchAccess = filterParams.get("branchIds");
        }
        filterParams.put("branchIds", branchAccess);
        return new CustomerInfoSpecBuilder(filterParams);
    }


    private ObjectMapper objectMapper() {
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
    static final String SHARE_SECURITY = "Share Security";
    static final String GUARANTOR = "Guarantor";
    static final String INSURANCE = "Insurance";
}


