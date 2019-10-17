package com.sb.solutions.web.accountPurpose.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountPurpose.service.AccountPurposeService;
import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.api.accountType.service.AccountTypeService;
import com.sb.solutions.core.dto.RestResponseDto;

@RestController
@RequestMapping(AccountPurposeController.URL)
public class AccountPurposeController {

    static final String URL = "/v1/accountPurpose";

    private final AccountPurposeService accountPurposeService;
    private final AccountTypeService accountTypeService;

    public AccountPurposeController(
        @Autowired AccountPurposeService accountPurposeService,
        @Autowired AccountTypeService accountTypeService
    ) {
        this.accountPurposeService = accountPurposeService;
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AccountPurpose accountPurpose) {
        AccountPurpose a = accountPurposeService.save(accountPurpose);
        return new RestResponseDto().successModel(a);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(accountPurposeService.findAll());
    }

    @GetMapping(value = "/accountType/{accountTypeId}")
    public ResponseEntity<?> getAccountPurposeByAccountType(
        @PathVariable Long accountTypeId) {
        AccountType accountType = accountTypeService.findOne(accountTypeId);
        return new RestResponseDto()
            .successModel(accountType.getAccountPurpose());
    }

}
