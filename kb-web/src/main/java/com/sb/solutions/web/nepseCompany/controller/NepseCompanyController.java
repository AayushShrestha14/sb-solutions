package com.sb.solutions.web.nepseCompany.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.service.NepseCompanyService;
import com.sb.solutions.api.nepseCompany.util.BulkConverter;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/nepse-company")
public class NepseCompanyController {

    private final NepseCompanyService nepseCompanyService;
    private final BulkConverter bulkConverter;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NepseCompany nepseCompany) {
        return new RestResponseDto().successModel(nepseCompanyService.save(nepseCompany));
    }

    @PostMapping(value = "bulk")
    public ResponseEntity<?> saveCompanyBulk(@RequestBody MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = bulkConverter.parseNepseCompanyFile(multipartFile);
        if (nepseCompanyList == null) {
            return new RestResponseDto().failureModel("Invalid file format");
        } else {
            nepseCompanyService.saveList(nepseCompanyList);
            return new RestResponseDto().successModel("Added");
        }
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(nepseCompanyService
            .findAllPageable(searchDto, PaginationUtils.pageable(page, size)));
    }

    @GetMapping("/statusCount")
    public ResponseEntity<?> getNepseCompanyStatusCount() {
        return new RestResponseDto().successModel(nepseCompanyService.nepseStatusCount());
    }
}
