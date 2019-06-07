package com.sb.solutions.web.loan.v1;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import com.sb.solutions.web.loan.v1.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rujan Maharjan on 5/10/2019
 */

@RestController
@RequestMapping(CustomerLoanController.URL)
@AllArgsConstructor
public class CustomerLoanController {

    static final String URL = "/v1/loan-customer";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private GlobalExceptionHandler globalExceptionHandler;
    private CustomerLoanService service;

    private Mapper mapper;


    @PostMapping(value = "/action")
    public ResponseEntity<?> loanAction(@RequestBody LoanActionDto actionDto, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        CustomerLoan c = mapper.ActionMapper(actionDto);
        this.save(c,bindingResult);
       // service.sendForwardBackwardLoan(mapper.ActionMapper(actionDto));
        return new RestResponseDto().successModel(actionDto);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CustomerLoan customerLoan, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("saving Customer Loan {}",customerLoan);

        return new RestResponseDto().successModel(service.save(customerLoan));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(service.findOne(id));
    }


    @PostMapping("/status")
    public ResponseEntity<?> getByDocStatus(@RequestBody CustomerLoan customerLoan) {
        logger.debug("getByDocStatus Customer Loan {}",customerLoan);
        return new RestResponseDto().successModel(service.getCustomerLoanByDocumentStatus(customerLoan.getDocumentStatus()));
    }

}
