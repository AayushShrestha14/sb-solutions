package com.sb.solutions.web.loanConfig;

import javax.validation.Valid;

import com.sb.solutions.web.eligibility.scheme.SchemeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author Rujan Maharjan on 2/25/2019
 */
@RestController
@RequestMapping("/v1/config")
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
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableLoanConfig(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(loanConfigService
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/get/statusCount")
    public ResponseEntity<?> getLoanStatusCount() {
        return new RestResponseDto().successModel(loanConfigService.loanStatusCount());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAll")
    public ResponseEntity<?> getLoanAll() {
        return new RestResponseDto().successModel(loanConfigService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get/{id}")
    public ResponseEntity<?> getLoanOne(@PathVariable Long id) {
        return new RestResponseDto().successModel(loanConfigService.findOne(id));
    }

}
