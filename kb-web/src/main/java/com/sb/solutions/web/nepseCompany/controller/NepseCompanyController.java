package com.sb.solutions.web.nepseCompany.controller;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.service.NepseCompanyService;
import com.sb.solutions.api.nepseCompany.util.BulkConverter;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.CustomPageable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/nepseCompany")
public class NepseCompanyController {
    private NepseCompanyService nepseCompanyService;
    private BulkConverter bulkConverter;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NepseCompany nepseCompany){
            return new RestResponseDto().successModel(nepseCompanyService.save(nepseCompany));
    }
    @PostMapping(value = "bulk")
    public ResponseEntity<?> saveCompanyBulk(@RequestBody MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = bulkConverter.parseNepseCompanyFile(multipartFile);
        if(nepseCompanyList == null){
            return new RestResponseDto().failureModel("Invalid file format");
        }
        else {
            nepseCompanyService.saveList(nepseCompanyList);
            return new RestResponseDto().successModel("Added");
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(nepseCompanyService.findAllPageable(searchDto,new CustomPageable().pageable(page, size)));
    }
    @GetMapping("/get/statusCount")
    public ResponseEntity<?> getNepseCompanyStatusCount() {
        return new RestResponseDto().successModel(nepseCompanyService.nepseStatusCount());
    }
}
