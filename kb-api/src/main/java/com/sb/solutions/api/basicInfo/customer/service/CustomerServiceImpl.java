package com.sb.solutions.api.basicInfo.customer.service;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import com.sb.solutions.api.basicInfo.customer.repository.CustomerRepository;
import com.sb.solutions.api.basicInfo.customerRelative.entity.CustomerRelative;
import com.sb.solutions.core.dto.SearchDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


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
        Date date = new Date();
        customer.setLastModifiedAt(date);
        if (customer.getCustomerRelatives().size() <= 0) {
            customer.setCustomerRelatives(null);
        } else {
            for (CustomerRelative relative : customer.getCustomerRelatives()) {
                relative.setLastModifiedAt(date);
            }
        }
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return customerRepository.customerFilter(s.getName() == null ? "" : s.getName(), pageable);
    }
}
