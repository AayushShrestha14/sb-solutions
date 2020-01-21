package com.sb.solutions.web.nepseCompany.controller;

import com.google.common.base.Preconditions;
import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.entity.NepseMaster;
import com.sb.solutions.api.nepseCompany.service.NepseCompanyService;
import com.sb.solutions.api.nepseCompany.service.NepseMasterService;
import com.sb.solutions.api.nepseCompany.util.NepseExcelReader;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/nepse-company")
public class NepseCompanyController {

    private final NepseCompanyService nepseCompanyService;
    private final NepseMasterService nepseMasterService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NepseCompany nepseCompany) {
        return new RestResponseDto().successModel(nepseCompanyService.save(nepseCompany));
    }

    @PostMapping(value = "/uploadNepseFile")
    public ResponseEntity<?> saveNepseExcel(
            @RequestParam("excelFile") MultipartFile multipartFile) {

        List<NepseCompany> nepseCompanyList = NepseExcelReader.parseNepseCompanyFile(multipartFile);
        if (nepseCompanyList == null) {
            return new RestResponseDto().failureModel("Failed-Either file is empty or invalid file format");
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
    public ResponseEntity<?> addShare(@RequestBody NepseMaster nepseMaster) {
        Preconditions.checkNotNull(nepseMaster.getOrdinary());
        Preconditions.checkNotNull(nepseMaster.getPromoter());
        return new RestResponseDto().successModel(nepseMasterService.save(nepseMaster));
    }

    @GetMapping(value = "/share")
    public ResponseEntity<?> getActiveShare() {
        return new RestResponseDto().successModel(nepseMasterService.findActiveMasterRecord());
    }

    @GetMapping(value = "/share/list")
    public ResponseEntity<?> getAllShare(@RequestParam("page") int page,
                                         @RequestParam("size") int size) {
        return new RestResponseDto().successModel(
                nepseMasterService.findNepseListOrderById(new Object(), PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getShare(@PathVariable("id") Long id) {
        return new RestResponseDto().successModel(nepseCompanyService.findOne(id));
    }
}
