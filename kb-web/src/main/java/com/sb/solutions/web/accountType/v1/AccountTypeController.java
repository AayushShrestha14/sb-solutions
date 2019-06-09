package com.sb.solutions.web.accountType.v1;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountType.entity.AccountType;
import com.sb.solutions.api.accountType.service.AccountTypeService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/accountType")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public ResponseEntity<?> saveAccountType(@Valid @RequestBody AccountType accountType, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
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

    @PostMapping(value = "/byAccountPurpose")
    public ResponseEntity<?> getAccountTypeBuAccountPurpose(@RequestBody AccountPurpose accountPurpose){
        return new RestResponseDto().successModel(accountTypeService.findAllByAccountPurpose(accountPurpose));
    }
}
