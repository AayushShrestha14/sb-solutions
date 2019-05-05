package com.sb.solutions.web.customer.controller;


import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.uploadFile.UploadFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customer")
@AllArgsConstructor
public class CustomerController {
    CustomerService customerService;
    GlobalExceptionHandler globalExceptionHandler;
    Gson gson;
    private UploadFile uploadFile;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        gson.toJson(customer);
        Customer c = customerService.save(customer);
        if (c == null) {
            return new RestResponseDto().failureModel("Error Occurs");
        } else {
            return new RestResponseDto().successModel(c);
        }
    }

    @GetMapping(value = "/getList")
    public ResponseEntity<?> getCustomer() {
        return new RestResponseDto().successModel(customerService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")})
    @RequestMapping(method = RequestMethod.POST, path = "/get")
    public ResponseEntity<?> getPageableCustomerByBranch(@RequestBody Branch branch, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto().successModel(customerService.findAllPageableByBranch(branch, PaginationUtils.pageable(page, size)));
    }
    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type) {
        System.out.println();
        return uploadFile.uploadFile(multipartFile,type);
    }
}
