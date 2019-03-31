package com.sb.solutions.web.company.controller;

import com.sb.solutions.api.company.entity.Company;
import com.sb.solutions.api.company.service.CompanyService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/company")
public class CompanyController {

    private final CompanyService companyService;
    @PostMapping
    public ResponseEntity<?> saveCompany(@RequestBody Company company) {
        return new RestResponseDto().successModel(companyService.save(company));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAllPage(@RequestBody Company company, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(companyService.findAllPageable(company,new CustomPageable().pageable(page, size)));
    }

    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getCompanyStatusCount() {
        return new RestResponseDto().successModel(companyService.companyStatusCount());
    }
}
