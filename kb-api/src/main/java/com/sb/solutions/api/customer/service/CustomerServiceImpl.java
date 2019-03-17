package com.sb.solutions.api.customer.service;

import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        customer.setLastModified(date);
        customer.getCustomerFather().setLastModified(date);
        customer.getCustomerGrandFather().setLastModified(date);
        customer.getCustomerSpouse().setLastModified(date);
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
