package com.sb.solutions.api.customer.service;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.creditRiskGrading.entity.CreditRiskGrading;
import com.sb.solutions.api.creditRiskGrading.service.CreditRiskGradingService;
import com.sb.solutions.api.creditRiskGradingAlpha.entity.CreditRiskGradingAlpha;
import com.sb.solutions.api.creditRiskGradingAlpha.service.CreditRiskGradingAlphaService;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.api.customer.repository.CustomerInfoRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerInfoSpecBuilder;
import com.sb.solutions.api.customerGroup.CustomerGroup;
import com.sb.solutions.api.financial.entity.Financial;
import com.sb.solutions.api.financial.service.FinancialService;
import com.sb.solutions.api.guarantor.entity.GuarantorDetail;
import com.sb.solutions.api.guarantor.service.GuarantorDetailService;
import com.sb.solutions.api.helper.HelperDto;
import com.sb.solutions.api.helper.HelperIdType;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.service.InsuranceService;
import com.sb.solutions.api.loanflag.service.CustomerLoanFlagService;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.service.SecurityService;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.service.ShareSecurityService;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.api.siteVisit.service.SiteVisitService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;
import com.sb.solutions.core.utils.PathBuilder;
import com.sb.solutions.report.core.bean.ReportParam;
import com.sb.solutions.report.core.enums.ExportType;
import com.sb.solutions.report.core.enums.ReportType;
import com.sb.solutions.report.core.factory.ReportFactory;
import com.sb.solutions.report.core.model.Report;

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
    private final CreditRiskGradingAlphaService creditRiskGradingAlphaService;
    private final CreditRiskGradingService creditRiskGradingService;
    private final CustomerLoanFlagService loanFlagService;

    public CustomerInfoServiceImpl(
        @Autowired CustomerInfoRepository customerInfoRepository,
        FinancialService financialService,
        SiteVisitService siteVisitService,
        SecurityService securityService,
        ShareSecurityService shareSecurityService,
        GuarantorDetailService guarantorDetailService,
        UserService userService,
        InsuranceService insuranceService,
        CreditRiskGradingAlphaService creditRiskGradingAlphaService,
        CreditRiskGradingService creditRiskGradingService,
        CustomerLoanFlagService loanFlagService) {
        super(customerInfoRepository);
        this.customerInfoRepository = customerInfoRepository;
        this.financialService = financialService;
        this.siteVisitService = siteVisitService;
        this.userService = userService;
        this.securityService = securityService;
        this.shareSecurityService = shareSecurityService;
        this.guarantorDetailService = guarantorDetailService;
        this.insuranceService = insuranceService;
        this.creditRiskGradingAlphaService = creditRiskGradingAlphaService;
        this.creditRiskGradingService = creditRiskGradingService;
        this.loanFlagService = loanFlagService;
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
            customerInfo.setIdRegDate(((CompanyInfo) o).getLegalStatus().getRegistrationDate());
            customerInfo.setIdNumber(((CompanyInfo) o).getPanNumber());
            customerInfo.setIdRegPlace(((CompanyInfo) o).getIssuePlace());
            customerInfo.setContactNo(((CompanyInfo) o).getContactNum());
            customerInfo.setEmail(((CompanyInfo) o).getEmail());
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
        } else if ((template.equalsIgnoreCase(TemplateName.FINANCIAL))) {

            final Financial financial = financialService
                .save(objectMapper().convertValue(o, Financial.class));
            customerInfo1.setFinancial(financial);
        } else if ((template.equalsIgnoreCase(TemplateName.SECURITY))) {

            final Security security = securityService
                .save(objectMapper().convertValue(o, Security.class));
            customerInfo1.setSecurity(security);
        } else if ((template.equalsIgnoreCase(TemplateName.SHARE_SECURITY))) {

            final ShareSecurity shareSecurity = shareSecurityService
                .save(objectMapper().convertValue(o, ShareSecurity.class));
            customerInfo1.setShareSecurity(shareSecurity);
            HelperDto<Long> helperDto = new HelperDto<>(customerInfoId, HelperIdType.CUSTOMER_INFO);
            shareSecurityService.execute(Optional.of(helperDto));
        } else if ((template.equalsIgnoreCase(TemplateName.GUARANTOR))) {
            final GuarantorDetail guarantors = guarantorDetailService
                .save(objectMapper().convertValue(o, GuarantorDetail.class));
            customerInfo1.setGuarantors(guarantors);
        } else if ((template.equalsIgnoreCase(TemplateName.INSURANCE))) {
            ObjectMapper mapper = new ObjectMapper();
            List<Insurance> insurances = Arrays.asList(mapper.convertValue(o, Insurance[].class));
            final List<Insurance> insurance = insuranceService.saveAll(insurances);
            customerInfo1.setInsurance(insurance);
            insuranceService
                .execute(Optional.of(new HelperDto<>(customerInfoId, HelperIdType.CUSTOMER_INFO)));
        } else if ((template.equalsIgnoreCase(TemplateName.CUSTOMER_GROUP))) {
            CustomerGroup customerGroup = objectMapper().convertValue(o, CustomerGroup.class);
            if (customerGroup.getId() == null && customerGroup.getGroupCode() == null) {
                customerInfo1.setCustomerGroup(null);
            } else {
                customerInfo1.setCustomerGroup(customerGroup);
            }
        } else if ((template.equalsIgnoreCase(TemplateName.CRG_ALPHA))) {
            final CreditRiskGradingAlpha creditRiskGradingAlpha = creditRiskGradingAlphaService
                .save(objectMapper().convertValue(o, CreditRiskGradingAlpha.class));
            customerInfo1.setCreditRiskGradingAlpha(creditRiskGradingAlpha);
        } else if ((template.equalsIgnoreCase(TemplateName.CRG))) {
            final CreditRiskGrading creditRiskGrading = creditRiskGradingService
                .save(objectMapper().convertValue(o, CreditRiskGrading.class));
            customerInfo1.setCreditRiskGrading(creditRiskGrading);
        }
        customerInfo1.setLoanFlags(loanFlagService.findAllByCustomerInfoId(customerInfoId));
        return customerInfoRepository.save(customerInfo1);
    }

    @Override
    public CustomerInfo findByAssociateIdAndCustomerType(Long id, CustomerType customerType) {
        return customerInfoRepository.findByAssociateIdAndCustomerType(id, customerType);
    }

    @Override
    public CustomerInfo findByCustomerTypeAndIdNumberAndIdRegPlaceAndIdTypeAndIdRegDate(
        CustomerType customerType, String idNumber, String idRegPlace,
        CustomerIdType customerIdType, Date date) {
        return customerInfoRepository
            .findByCustomerTypeAndIdNumberAndIdRegPlaceAndIdTypeAndIdRegDate(customerType, idNumber,
                idRegPlace, customerIdType, date);
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

    @Override
    public String title() {
        return "Customer Report";
    }

    @Override
    public List<AbstractColumn> columns() {

        Format format = new Format() {
            @Override
            public StringBuffer format(Object o, StringBuffer stringBuffer,
                FieldPosition fieldPosition) {
                return stringBuffer.append(o.toString().toUpperCase());
            }

            @Override
            public Object parseObject(String s, ParsePosition parsePosition) {
                return null;
            }
        };
        AbstractColumn columnBranch = ColumnBuilder.getNew()
            .setColumnProperty("branch.name", String.class.getName())
            .setTitle("Branch").setWidth(100)
            .build();

        AbstractColumn columnName = ColumnBuilder.getNew()
            .setColumnProperty("name", String.class.getName())
            .setTitle("Name").setWidth(100)
            .build();
        AbstractColumn customerType = ColumnBuilder.getNew()
            .setColumnProperty("customerType", CustomerType.class.getName())
            .setTitle("Customer Type").setWidth(100).setTextFormatter(format)
            .build();

        AbstractColumn idType = ColumnBuilder.getNew()
            .setColumnProperty("idType", CustomerIdType.class.getName())
            .setTitle("  ID Type  ").setWidth(100).setTextFormatter(format)
            .build();
        AbstractColumn idNumber = ColumnBuilder.getNew()
            .setColumnProperty("idNumber", String.class.getName())
            .setTitle("ID Number").setWidth(100)
            .build();

        AbstractColumn issuePlace = ColumnBuilder.getNew()
            .setColumnProperty("idRegPlace", String.class.getName())
            .setTitle("ID Issue Place").setWidth(100)
            .build();
        AbstractColumn email = ColumnBuilder.getNew()
            .setColumnProperty("email", String.class.getName())
            .setTitle("Email").setWidth(100)
            .build();
        AbstractColumn contactNumber = ColumnBuilder.getNew()
            .setColumnProperty("contactNo", String.class.getName())
            .setTitle("Contact No.").setWidth(100)
            .build();
        AbstractColumn columnAssociate = ColumnBuilder.getNew()
            .setColumnProperty("createdAt", Date.class.getName())
            .setTitle("Associate Since").setWidth(100).setPattern("dd MMM, YYYY")
            .build();
        return Arrays
            .asList(columnBranch, columnName, customerType, idType, idNumber, issuePlace, email,
                contactNumber, columnAssociate);
    }

    @Override
    public ReportParam populate(Optional optional) {
        String filePath = new PathBuilder(UploadDir.initialDocument)
            .buildBuildFormDownloadPath("customer");
        Map<String, String> map = objectMapper().convertValue(optional.get(), Map.class);
        Specification<CustomerInfo> specification = getSpec(map).build();
        List customerInfoList = customerInfoRepository.findAll(specification);
        String filterBy = "";
        StringBuilder sb = new StringBuilder();
        map.values().removeIf(Objects::isNull);
        map.entrySet().forEach(a -> {
            String k = a.getKey();
            String v = a.getValue();
            if (!k.equalsIgnoreCase("branchIds")) {
                StringBuilder spaceCamelCase = new StringBuilder();
                String[] camelCase = StringUtils.splitByCharacterTypeCamelCase(k);
                for (String s : camelCase) {
                    spaceCamelCase.append(StringUtils.capitalize(s)).append(" ");
                }
                sb.append(spaceCamelCase).append(" = ").append(v).append(", ");
            }

        });
        if (!ObjectUtils.isEmpty(sb.toString())) {
            filterBy = " Filter By : " + sb.toString().substring(0, sb.toString().length() - 2);
        }
        return ReportParam.builder().reportName("Customer Report").title(title())
            .subTitle(filterBy).columns(columns()).data(customerInfoList)
            .reportType(ReportType.FORM_REPORT)
            .filePath(UploadDir.WINDOWS_PATH + filePath).exportType(ExportType.XLS)
            .build();

    }

    @Override

    public String csv(Object searchDto) {
        Report report = ReportFactory.getReport(populate(Optional.of(searchDto)));
        return new PathBuilder(UploadDir.initialDocument)
            .buildBuildFormDownloadPath("customer") + report.getFileName();

    }

}


class TemplateName {

    static final String SITE_VISIT = "SiteVisit";
    static final String FINANCIAL = "Financial";
    static final String SECURITY = "Security";
    static final String SHARE_SECURITY = "Share Security";
    static final String GUARANTOR = "Guarantor";
    static final String INSURANCE = "Insurance";
    static final String CUSTOMER_GROUP = "customerGroup";
    static final String CRG_ALPHA = "CrgAlpha";
    static final String CRG = "Crg";
}


