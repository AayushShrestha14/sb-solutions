package com.sb.solutions.web.loan.v1;

import com.sb.solutions.api.Loan.service.LoanService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import com.sb.solutions.web.loan.v1.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rujan Maharjan on 5/10/2019
 */

@RestController
@RequestMapping(LoanActionController.URL)
@AllArgsConstructor
public class LoanActionController {

    static final String URL = "/v1/loan-action";

    private static final Logger logger = LoggerFactory.getLogger(LoanActionController.class);

    private GlobalExceptionHandler globalExceptionHandler;

    private LoanService service;

    private Mapper mapper;


    @PostMapping
    public ResponseEntity<?> loanAction(@RequestBody LoanActionDto actionDto, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        service.sendForwardBackwardLoan(mapper.ActionMapper(actionDto));
        return new RestResponseDto().successModel(actionDto);
    }

}
