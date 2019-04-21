package com.sb.solutions.web.eligibility.company;

import com.sb.solutions.api.eligibility.company.entity.Company;
import com.sb.solutions.api.eligibility.company.service.CompanyService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/companies")
@AllArgsConstructor
public class CompanyController {

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    final public ResponseEntity<?> addCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to save new company.");
        final Company savedCompany = companyService.save(company);
        if (company == null) return new RestResponseDto().failureModel("Oops. Something went wrong.");
        return new RestResponseDto().successModel(savedCompany);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping
    final public ResponseEntity<?> getCompanies(@RequestParam("page") int page, @RequestParam("size") int size) {
        logger.debug("Request to get page of companies.");
        return new RestResponseDto().successModel(companyService
                .findAllPageable(null, new CustomPageable().pageable(page, size)));
    }

    @PutMapping(path = "/{id}")
    final public ResponseEntity<?> updateCompany(@PathVariable long id, @Valid @RequestBody Company company
    , BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        logger.debug("Request to update company with id [{}].", id);
        final Company updatedCompany = companyService.save(company);
        if (updatedCompany == null) return new RestResponseDto().failureModel("Oops. Something went wrong.");
        return new RestResponseDto().successModel(updatedCompany);
    }

}
