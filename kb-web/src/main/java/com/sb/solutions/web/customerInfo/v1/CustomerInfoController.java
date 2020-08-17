package com.sb.solutions.web.customerInfo.v1;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.customer.service.CustomerInfoService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
@RestController
@RequestMapping(CustomerInfoController.URL)
public class CustomerInfoController {

    public static final String URL = "/v1/customer-info";
    private final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
    private final CustomerInfoService customerInfoService;


    @Autowired
    public CustomerInfoController(
        CustomerInfoService customerInfoService
    ) {
        this.customerInfoService = customerInfoService;

    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageable(@RequestBody Map<String, String> searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(customerInfoService.findPageableBySpec(searchDto, PaginationUtils
                .pageable(page, size)));
    }

    @PostMapping("/{customerInfoId}/{template}")
    public ResponseEntity<?> saveCustomerLoanInfo(@RequestBody Object loanInfo,
        @PathVariable("customerInfoId") Long customerInfoId,
        @PathVariable("template") String template) {
        return new RestResponseDto()
            .successModel(
                customerInfoService.saveLoanInformation(loanInfo, customerInfoId, template));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerInfoByID(
        @PathVariable("id") Long id) {
        return new RestResponseDto()
            .successModel(customerInfoService.findOne(id).get());
    }

}
