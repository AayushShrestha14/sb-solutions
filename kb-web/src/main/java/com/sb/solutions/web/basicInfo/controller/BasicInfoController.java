package com.sb.solutions.web.basicInfo.controller;

import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import com.sb.solutions.api.basicInfo.customer.service.CustomerService;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/basicInfo")
public class BasicInfoController {
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveBasicInfo(@RequestBody Customer customer){
        return new RestResponseDto().successModel(customerService.save(customer));
    }
}
