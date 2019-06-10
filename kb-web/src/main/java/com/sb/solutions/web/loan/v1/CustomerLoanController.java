package com.sb.solutions.web.loan.v1;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.web.loan.v1.dto.LoanActionDto;
import com.sb.solutions.web.loan.v1.mapper.Mapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAllByPagination(@RequestBody Object searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(service.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value="/count")
    public ResponseEntity<?>  countLoanStatus(){
        return new RestResponseDto().successModel(service.statusCount());
    }

}
