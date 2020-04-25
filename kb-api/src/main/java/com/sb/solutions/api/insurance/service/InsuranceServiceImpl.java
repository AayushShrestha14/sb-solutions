package com.sb.solutions.api.insurance.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.api.emailConfig.service.EmailConfigService;
import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.repository.InsuranceRepository;
import com.sb.solutions.api.insurance.repository.spec.InsuranceSpecBuilder;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.service.NotificationMasterService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.EmailConstant.Template;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.NotificationMasterType;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailThreadService;

@Service("insuranceService")
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance, Long> implements
    InsuranceService {

    @Value("${bank.name}")
    private String bankName;

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    private final InsuranceRepository repository;
    private final NotificationMasterService notificationMasterService;
    private final CustomerLoanRepository customerLoanRepository;
    private final MailThreadService mailThreadService;
    private final BranchService branchService;
//    private final EmailConfigService emailConfigService;
//    private final CustomerLoanService customerLoanService;
    private final UserService userService;

    protected InsuranceServiceImpl(
        InsuranceRepository repository,
        NotificationMasterService notificationMasterService,
        CustomerLoanRepository customerLoanRepository,
        BranchService branchService,
        MailThreadService mailThreadService,
        EmailConfigService emailConfigService,
        UserService userService) {
        super(repository);

        this.repository = repository;
        this.notificationMasterService = notificationMasterService;
        this.customerLoanRepository = customerLoanRepository;
        this.mailThreadService = mailThreadService;
        this.branchService = branchService;
//        this.customerLoanService  = customerLoanService;
//        this.emailConfigService = emailConfigService;
        this.userService = userService;
    }


    @Override
    protected BaseSpecBuilder<Insurance> getSpec(Map<String, String> filterParams) {
        return new InsuranceSpecBuilder(filterParams);
    }

    @Override
    public void execute(Optional<Long> optional) {
        Map<String, String> insuranceFilter = new HashMap<String, String>() {{
            put("notificationKey", NotificationMasterType.INSURANCE_EXPIRY_NOTIFY.toString());
        }};
        NotificationMaster notificationMaster = notificationMasterService
            .findOneBySpec(insuranceFilter).orElse(null);
        if (notificationMaster != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int daysToExpire = notificationMaster.getValue();
            c.add(Calendar.DAY_OF_MONTH, daysToExpire);

            Map<String, String> t = new HashMap<String, String>() {{
                put("hasInsurance", "true");
                put("documentStatus", DocStatus.APPROVED.name());
                put("isInsuranceNotified","false");
            }};
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(t, Map.class);
            CustomerLoanSpecBuilder builder = new CustomerLoanSpecBuilder(map);
            Specification<CustomerLoan> specification = builder.build();
            List<CustomerLoan> loans = optional
                .map(id -> Collections.singletonList(customerLoanRepository.getOne(id)))
                .orElseGet(() -> customerLoanRepository.findAll(specification));
            Email email = new Email();
            email.setBankName(this.bankName);

            for (CustomerLoan loan : loans) {
                try {
                    /* expired insurance, set expiry flag */
                    String remarks = "Insurance expiry date is about to meet.";
                    boolean flag = loan.getInsurance().getExpiryDate().compareTo(c.getTime()) <= 0;
                    customerLoanRepository.setInsuranceExpiryFlag(loan.getId(), remarks, flag);
                    customerLoanRepository.setInsuranceNotifiedFlag(loan.getId(),false);
                    if(flag) {
                        User userMaker = userService.findOne((loan.getInsurance().getCreatedBy()));
                        email.setTo(userMaker.getEmail());
                        email.setToName(userMaker.getName());
                        email.setName(loan.getCustomerInfo().getCustomerName());
                        email.setExpiryDate(loan.getInsurance().getExpiryDate());
                        email.setEmail(loan.getCustomerInfo().getEmail());
                        email.setPhoneNumber(loan.getCustomerInfo().getContactNumber());
                        email.setLoanTypes(loan.getLoanType());
                        email.setClientCitizenshipNumber(
                            loan.getCustomerInfo().getCitizenshipNumber());
                        email.setBankBranch(loan.getBranch().getName());
                        email.setInsuranceCompanyName(loan.getInsurance().getCompany());
                        Template templateMaker = Template.INSURANCE_EXPIRY_MAKER;
                        templateMaker.setSubject("Insurance Expiry Notice: " + email.getName());
                        sendInsuranceEmail(templateMaker, email);
                        if (loan.getDocumentStatus() == DocStatus.APPROVED) {
                            customerLoanRepository.setInsuranceNotifiedFlag(loan.getId(), true);
                        }
//                        else {
//                            customerLoanRepository.setInsuranceNotifiedFlag(loan.getId(),false);
//                        }
                        if (loan.getCustomerInfo().getEmail() != null) {
                            try {
                                email.setToName(loan.getCustomerInfo().getCustomerName());
                                email.setTo(loan.getCustomerInfo().getEmail());
                                email.setEmail(userMaker.getEmail());
                                Branch branch = branchService.findOne(loan.getBranch().getId());
                                email.setPhoneNumber(branch.getLandlineNumber());
                                Template templateClient = Template.INSURANCE_EXPIRY_CLIENT;
                                templateClient.setSubject(
                                    "Insurance Expiry Notice related to " + email.getLoanTypes()
                                        + " at " + bankName);
                                this.sendInsuranceEmail(templateClient, email);
                            } catch (Exception e) {
                                LOGGER.error("Error sending insurance email to Client");
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    LOGGER
                        .error("Error updating insurance expiry flag {}", e.getLocalizedMessage());
                }
            }
        }
    }

    public void sendInsuranceEmail(Template template,Email email){
        try {
            mailThreadService.testMail(template, email);
            LOGGER.info(" sending Insurance Email ");
        } catch (Exception e) {
            LOGGER.error("Error while sending Insurance Email", e);
        }
    }
}
