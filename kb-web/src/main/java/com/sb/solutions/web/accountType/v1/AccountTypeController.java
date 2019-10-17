package com.sb.solutions.web.accountType.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(AccountTypeController.URL)
public class AccountTypeController {

    static final String URL = "/v1/accountType";

    private final AccountTypeService accountTypeService;

    public AccountTypeController(
        @Autowired AccountTypeService accountTypeService
    ) {
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AccountType accountType) {
        AccountType a = accountTypeService.save(accountType);
        if (a == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(a);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new RestResponseDto().successModel(accountTypeService.findOne(id));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(accountTypeService.findAll());
    }

}
