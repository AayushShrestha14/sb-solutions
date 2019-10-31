package com.sb.solutions.web.customer.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.service.CustomerService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.utils.PaginationUtils;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);

        if (null == savedCustomer) {
            logger.error("Error while saving customer {}", customer);
            return new RestResponseDto()
                .failureModel("Error occurred while saving Customer " + customer);
        }

        return new RestResponseDto().successModel(savedCustomer);
    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageable(@RequestBody Object searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(customerService.findAllPageable(searchDto, PaginationUtils
                .pageable(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return new RestResponseDto().successModel(customerService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Customer customer) {

        final Customer savedCustomer = customerService.save(customer);

        if (null == savedCustomer) {
            return new RestResponseDto()
                .failureModel("Error occurred while updating Customer " + customer);
        }

        return new RestResponseDto().successModel(savedCustomer);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new RestResponseDto().successModel(customerService.findAll());
    }

    @PostMapping("/search")
    public ResponseEntity<?> getByCustomerId(@RequestBody String customerId){
        return new RestResponseDto()
                    .successModel(customerService.findCustomerByCustomerId(customerId));
    }
}
