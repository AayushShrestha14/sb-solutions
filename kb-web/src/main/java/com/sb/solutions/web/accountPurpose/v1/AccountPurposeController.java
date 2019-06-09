package com.sb.solutions.web.accountPurpose.v1;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountPurpose.repository.AccountPurposeRepository;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/accountPurpose")
public class AccountPurposeController {
    private final AccountPurposeRepository accountPurposeRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public ResponseEntity<?> saveAccountPurpose(@Valid @RequestBody AccountPurpose accountPurpose, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        AccountPurpose a = accountPurposeRepository.save(accountPurpose);
        if (a == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(a);
        }
    }


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAccountPurpose() {
        return new RestResponseDto().successModel(accountPurposeRepository.findAll());
    }

}
