package com.sb.solutions.web.accountType.v1;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.api.accountType.service.AccountTypeService;
import com.sb.solutions.core.dto.RestResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/accountType")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @PostMapping
    public ResponseEntity<?> saveAccountType(@Valid @RequestBody AccountType accountType,
        BindingResult bindingResult) {
        AccountType a = accountTypeService.save(accountType);
        if (a == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(a);
        }
    }


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAccountType() {
        return new RestResponseDto().successModel(accountTypeService.findAll());
    }

    @GetMapping(value = "/accountPurpose/{accountPurposeId}")
    public ResponseEntity<?> getAccountTypeBuAccountPurpose(
        @PathVariable Long accountPurposeId) {
        return new RestResponseDto()
            .successModel(accountTypeService.findAllByAccountPurposeId(accountPurposeId));
    }
}
