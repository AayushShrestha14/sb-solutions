package com.sb.solutions.web.companyInfo.v1;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.service.EntityInfoService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/companyInfo")
public class CompanyInfoController {

    private final EntityInfoService entityInfoService;

    public CompanyInfoController(@Autowired EntityInfoService entityInfoService) {
        this.entityInfoService = entityInfoService;
    }

    @PostMapping
    public ResponseEntity<?> saveCompanyInfo(@Valid @RequestBody EntityInfo entityInfo) {
        return new RestResponseDto().successModel(entityInfoService.save(entityInfo));
    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageable(@RequestBody Object searchDto,
                                         @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
                .successModel(entityInfoService.findAllPageable(searchDto, PaginationUtils
                        .pageable(page, size)));
    }
}
