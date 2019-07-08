package com.sb.solutions.web.companyInfo.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.service.EntityInfoService;
import com.sb.solutions.core.dto.RestResponseDto;

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
}
