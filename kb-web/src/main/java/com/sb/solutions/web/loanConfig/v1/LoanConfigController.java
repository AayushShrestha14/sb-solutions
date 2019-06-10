package com.sb.solutions.web.loanConfig.v1;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Rujan Maharjan on 2/25/2019
 */
@RestController
@RequestMapping("/v1/loan-configs")
public class LoanConfigController {

    private final Logger logger = LoggerFactory.getLogger(LoanConfigController.class);

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    LoanConfigService loanConfigService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveLoanConfiguration(@Valid @RequestBody LoanConfig config,
        BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to save new loan.");
        LoanConfig loanConfig = loanConfigService.save(config);
        if (loanConfig == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(loanConfig);
        }
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/list")
    public ResponseEntity<?> getPageableLoanConfig(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanConfigService
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/statusCount")
    public ResponseEntity<?> getLoanStatusCount() {
        return new RestResponseDto().successModel(loanConfigService.loanStatusCount());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public ResponseEntity<?> getLoanAll() {
        return new RestResponseDto().successModel(loanConfigService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getLoanOne(@PathVariable Long id) {
        return new RestResponseDto().successModel(loanConfigService.findOne(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/status")
    public ResponseEntity<?> getLoanByStatus(@RequestBody Status status) {
        return new RestResponseDto().successModel(loanConfigService.getAllByStatus(status));
    }

}
