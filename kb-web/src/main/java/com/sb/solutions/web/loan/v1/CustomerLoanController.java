package com.sb.solutions.web.loan.v1;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.service.CustomerLoanService;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.web.common.stage.dto.StageDto;
import com.sb.solutions.web.loan.v1.mapper.Mapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author Rujan Maharjan on 5/10/2019
 */

@RestController
@RequestMapping(CustomerLoanController.URL)
public class CustomerLoanController {

    static final String URL = "/v1/Loan-customer";

    private static final Logger logger = LoggerFactory.getLogger(CustomerLoanController.class);

    private final CustomerLoanService service;

    private final UserService userService;

    private final Mapper mapper;

    public CustomerLoanController(
        @Autowired CustomerLoanService service,
        @Autowired Mapper mapper,
        @Autowired UserService userService) {

        this.service = service;
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping(value = "/action")
    public ResponseEntity<?> loanAction(@Valid @RequestBody StageDto actionDto) {
        final CustomerLoan c = mapper
            .actionMapper(actionDto, service.findOne(actionDto.getCustomerLoanId()),
                userService.getAuthenticated());
        service.sendForwardBackwardLoan(c);
        return new RestResponseDto().successModel(actionDto);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CustomerLoan customerLoan,
        BindingResult bindingResult) {

        logger.debug("saving Customer Loan {}", customerLoan);

        return new RestResponseDto().successModel(service.save(customerLoan));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(service.findOne(id));
    }


    @PostMapping("/status")
    public ResponseEntity<?> getfirst5ByDocStatus(@RequestBody CustomerLoan customerLoan) {
        logger.debug("getByDocStatus Customer Loan {}", customerLoan);
        return new RestResponseDto().successModel(
            service.getFirst5CustomerLoanByDocumentStatus(customerLoan.getDocumentStatus()));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAllByPagination(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(service.findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value = "/statusCount")
    public ResponseEntity<?> countLoanStatus() {
        return new RestResponseDto().successModel(service.statusCount());
    }

    @GetMapping(value = "/proposed-amount")
    public ResponseEntity<?> getProposedAmount() {
        return new RestResponseDto().successModel(service.proposedAmount());
    }

    @GetMapping(value = "/loan-amount/{id}")
    public ResponseEntity<?> getProposedAmountByBranch(@PathVariable Long id) {
        return new RestResponseDto().successModel(service.proposedAmountByBranch(id));
    }
}
