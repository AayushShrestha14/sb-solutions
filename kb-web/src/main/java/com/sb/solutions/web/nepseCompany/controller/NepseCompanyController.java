package com.sb.solutions.web.nepseCompany.controller;

import java.util.List;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.entity.ShareValue;
import com.sb.solutions.api.nepseCompany.service.NepseCompanyService;
import com.sb.solutions.api.nepseCompany.service.ShareValueService;
import com.sb.solutions.api.nepseCompany.util.ExcelReader;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/nepse-company")
public class NepseCompanyController {

    private final NepseCompanyService nepseCompanyService;
    private final ExcelReader excelReader;
    private final ShareValueService shareValueService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NepseCompany nepseCompany) {
        return new RestResponseDto().successModel(nepseCompanyService.save(nepseCompany));
    }

    @PostMapping(value = "/uploadNepseFile")
    public ResponseEntity<?> saveNepseExcel(
        @RequestParam("excelFile") MultipartFile multipartFile) {

        List<NepseCompany> nepseCompanyList = excelReader.parseNepseCompanyFile(multipartFile);
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

    @PostMapping(value = "/nepse-list")
    public ResponseEntity<?> getNepseList(@RequestBody Object object) {
        return new RestResponseDto()
            .successModel(nepseCompanyService.getAllNepseBySearchDto(object));
    }

    @GetMapping("/statusCount")
    public ResponseEntity<?> getNepseCompanyStatusCount() {
        return new RestResponseDto().successModel(nepseCompanyService.nepseStatusCount());
    }

    @PostMapping(value = "/share")
    public ResponseEntity<?> addShare(@RequestBody ShareValue shareValue) {
        Preconditions.checkNotNull(shareValue.getShareData());
        return new RestResponseDto().successModel(shareValueService.save(shareValue));
    }

    @GetMapping(value = "/share/list")
    public ResponseEntity<?> getAllShare(@RequestParam("page") int page,
        @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
            shareValueService.findAllPageable(new Object(), PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getShare(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(nepseCompanyService.findOne(id));
    }
}
