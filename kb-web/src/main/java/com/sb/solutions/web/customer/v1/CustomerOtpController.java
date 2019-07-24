package com.sb.solutions.web.customer.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.customerOtp.service.CustomerOtpService;

@RestController
@RequestMapping("/v1/customer-otp")
public class CustomerOtpController {

    private final CustomerOtpService customerOtpService;

    public CustomerOtpController(
        @Autowired CustomerOtpService customerOtpService
    ) {
        this.customerOtpService = customerOtpService;
    }

}
