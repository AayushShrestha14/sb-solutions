/*
package com.sb.solutions.api.customer.service;

import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.repository.CustomerRepository;
import com.sb.solutions.core.dto.SearchDto;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findOne(Long id) {
        return customerRepository.getOne(id);
    }

    @Override
    public Customer save(Customer customer) {
        //customer.setCustomerDetailsJson(gson.toJson(customer.getCustomerDetails()));
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(t, SearchDto.class);
        return customerRepository.customerFilter("", pageable);
    }

    @Override
    public Page<Customer> findAllPageableByBranch(Branch branch, Pageable pageable) {
        return customerRepository.findAllByBranch(branch,pageable);
    }
}
*/
