package com.sb.solutions.web.eligibility.company;

import com.sb.solutions.api.eligibility.company.entity.Company;
import com.sb.solutions.api.eligibility.company.service.CompanyService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "/v1")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping(path = "/admin/companies")
    final public ResponseEntity<?> addCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        final Company savedCompany = companyService.save(company);
        if (company == null) return new RestResponseDto().failureModel("Oops. Something went wrong.");
        return new RestResponseDto().successModel(savedCompany);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @GetMapping(path = "/companies")
    final public ResponseEntity<?> getCompanies(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(companyService
                .findAllPageable(null, new CustomPageable().pageable(page, size)));
    }
}
