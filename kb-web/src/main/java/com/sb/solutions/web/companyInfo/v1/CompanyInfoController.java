package com.sb.solutions.web.companyInfo.v1;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.service.EntityInfoService;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/companyInfo")
public class CompanyInfoController {
    private final EntityInfoService entityInfoService;
    @PostMapping
    public ResponseEntity<?> saveCompanyInfo(@RequestBody EntityInfo entityInfo){
        return new RestResponseDto().successModel(entityInfoService.save(entityInfo));
    }
}
