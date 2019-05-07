package com.sb.solutions.web.basicInfo.controller;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/basicInfo")
public class BasicInfoController {
    private CustomerService customerService;
    GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    public ResponseEntity<?> saveBasicInfo(@RequestBody Customer customer, BindingResult bindingResult){
        globalExceptionHandler.constraintValidation(bindingResult);
        return new RestResponseDto().successModel(customerService.save(customer));
    }
}
