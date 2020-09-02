package com.sb.solutions.web.insurance.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.entity.InsuranceHistory;
import com.sb.solutions.api.insurance.service.InsuranceHistoryService;
import com.sb.solutions.api.insurance.service.InsuranceService;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.service.CustomerLoanService;
import com.sb.solutions.core.dto.RestResponseDto;

/**
 * @author Elvin Shrestha on 4/19/2020
 */
@RestController
@RequestMapping(InsuranceController.URL)
public class InsuranceController {

    static final String URL = "/v1/insurance";
    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceController.class);

    private final InsuranceHistoryService insuranceHistoryService;
    private final InsuranceService insuranceService;
    private final CustomerLoanService customerLoanService;

    public InsuranceController(
        InsuranceHistoryService insuranceHistoryService,
        InsuranceService insuranceService,
        CustomerLoanService customerLoanService) {
        this.insuranceHistoryService = insuranceHistoryService;
        this.insuranceService = insuranceService;
        this.customerLoanService = customerLoanService;
    }

    @PostMapping("/history")
    public ResponseEntity<?> save(@RequestParam Long loanId, @RequestBody Insurance entity) {
        CustomerLoan customerLoan = customerLoanService.findOne(loanId);

        if (customerLoan == null) {
            return new RestResponseDto().failureModel(HttpStatus.NOT_FOUND, "Loan not found");
        }

        if (entity.getId() == null) {
            List<Insurance> old = customerLoan.getInsurance();
            old.forEach(value -> {
                // save to history
                InsuranceHistory history = new InsuranceHistory();
                BeanUtils.copyProperties(value, history);
                history.setCustomerLoanId(loanId);
                history.setId(null);    // new instance
                insuranceHistoryService.save(history);

                // update current
                BeanUtils.copyProperties(entity, value);
                Long oldId = value.getId();
                value.setId(oldId);
                insuranceService.save(value);

                customerLoanService.postLoanConditionCheck(customerLoanService.findOne(loanId));
            });
            return new RestResponseDto().successModel("Insurance detail updated");
        } else {
            insuranceService.save(entity);
            customerLoanService.postLoanConditionCheck(customerLoanService.findOne(loanId));
            return new RestResponseDto().successModel("Insurance detail overridden");
        }
    }
}
