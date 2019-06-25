package com.sb.solutions.web.basicInfo.v1;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/basicInfo")
public class BasicInfoController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveBasicInfo(@RequestBody Customer customer) {
        return new RestResponseDto().successModel(customerService.save(customer));
    }
}