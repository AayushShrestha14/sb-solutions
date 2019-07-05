package com.sb.solutions.web.accountPurpose.v1;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountPurpose.repository.AccountPurposeRepository;
import com.sb.solutions.core.dto.RestResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/accountPurpose")
public class AccountPurposeController {

    private final AccountPurposeRepository accountPurposeRepository;

    @PostMapping
    public ResponseEntity<?> saveAccountPurpose(@Valid @RequestBody AccountPurpose accountPurpose,
        BindingResult bindingResult) {
        AccountPurpose a = accountPurposeRepository.save(accountPurpose);
        return new RestResponseDto().successModel(a);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAccountPurpose() {
        return new RestResponseDto().successModel(accountPurposeRepository.findAll());
    }

}
