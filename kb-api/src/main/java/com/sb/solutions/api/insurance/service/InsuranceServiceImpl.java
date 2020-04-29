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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.repository.InsuranceRepository;
import com.sb.solutions.api.insurance.repository.spec.InsuranceSpecBuilder;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loan.repository.specification.CustomerLoanSpecBuilder;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.loanflag.service.CustomerLoanFlagService;
import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.service.NotificationMasterService;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanFlag;
import com.sb.solutions.core.enums.NotificationMasterType;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;

@Service("insuranceService")
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance, Long> implements
    InsuranceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    private final InsuranceRepository repository;
    private final NotificationMasterService notificationMasterService;
    private final CustomerLoanRepository customerLoanRepository;
    private final CustomerLoanFlagService customerLoanFlagService;

    protected InsuranceServiceImpl(
        InsuranceRepository repository,
        NotificationMasterService notificationMasterService,
        CustomerLoanRepository customerLoanRepository,
        CustomerLoanFlagService customerLoanFlagService) {
        super(repository);

        this.repository = repository;
        this.notificationMasterService = notificationMasterService;
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanFlagService = customerLoanFlagService;
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
            }};
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(t, Map.class);
            CustomerLoanSpecBuilder builder = new CustomerLoanSpecBuilder(map);
            Specification<CustomerLoan> specification = builder.build();
            List<CustomerLoan> loans = optional
                .map(id -> Collections.singletonList(customerLoanRepository.getOne(id)))
                .orElseGet(() -> customerLoanRepository.findAll(specification));
            for (CustomerLoan loan : loans) {
                try {
                    CustomerLoanFlag customerLoanFlag = customerLoanFlagService
                        .findCustomerLoanFlagByFlagAndCustomerLoanId(LoanFlag.INSURANCE_EXPIRY,
                            loan.getId());
                    boolean flag = loan.getInsurance().getExpiryDate().compareTo(c.getTime()) <= 0;
                    if (flag && customerLoanFlag == null) {
                        customerLoanFlag = new CustomerLoanFlag();
                        customerLoanFlag.setFlag(LoanFlag.INSURANCE_EXPIRY);
                        customerLoanFlag.setDescription(LoanFlag.INSURANCE_EXPIRY.getValue()[1]);
                        customerLoanFlag.setOrder(
                            Integer.parseInt(LoanFlag.INSURANCE_EXPIRY.getValue()[0]));
                        customerLoanFlag.setCustomerLoan(loan);
                        customerLoanFlagService.save(customerLoanFlag);
                    } else if (!flag && customerLoanFlag != null) {
                        customerLoanFlagService.deleteById(customerLoanFlag.getId());
                    }
                } catch (NullPointerException e) {
                    LOGGER
                        .error("Error updating insurance expiry flag {}", e.getMessage());
                }
            }
        }

    }
}
