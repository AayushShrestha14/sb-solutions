package com.sb.solutions.web.customer.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.customerOtp.entity.CustomerOtp;
import com.sb.solutions.api.customerOtp.service.CustomerOtpService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.web.customer.v1.dto.CustomerOtpDto;
import com.sb.solutions.web.customer.v1.dto.CustomerOtpTokenDto;
import com.sb.solutions.web.customer.v1.mapper.CustomerOtpMapper;
import com.sb.solutions.web.customer.v1.mapper.CustomerOtpTokenMapper;

@RestController
@RequestMapping("/v1/customer-otp")
public class CustomerOtpController {

    private final CustomerOtpService customerOtpService;
    private final CustomerOtpMapper customerOtpMapper;
    private final CustomerOtpTokenMapper customerOtpTokenMapper;

    public CustomerOtpController(
        @Autowired CustomerOtpService customerOtpService,
        @Autowired CustomerOtpMapper customerOtpMapper,
        @Autowired CustomerOtpTokenMapper customerOtpTokenMapper
    ) {
        this.customerOtpService = customerOtpService;
        this.customerOtpMapper = customerOtpMapper;
        this.customerOtpTokenMapper = customerOtpTokenMapper;
    }

    @PostMapping
    public ResponseEntity<?> generateOTP(@RequestBody CustomerOtpDto customerOtpDto) {
        final CustomerOtp customerOtp = customerOtpService
            .save(customerOtpMapper.mapDtoToEntity(customerOtpDto));
        return new RestResponseDto().successModel(customerOtpMapper.mapEntityToDto(customerOtp));
    }

    @PostMapping(value = "/verify")
    public ResponseEntity<?> verifyOTP(@RequestBody CustomerOtpTokenDto customerOtpTokenDto) {
        return new RestResponseDto().successModel(null);
    }

}
