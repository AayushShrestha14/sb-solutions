package com.sb.solutions.api.Loan.service;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.Loan.repository.CustomerLoanRepository;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    @Autowired
    CustomerLoanRepository customerLoanRepository;

    @Override
    public List<CustomerLoan> findAll() {
        return customerLoanRepository.findAll();
    }

    @Override
    public CustomerLoan findOne(Long id) {
        return customerLoanRepository.findById(id).get();
    }

    @Override
    public CustomerLoan save(CustomerLoan customerLoan) {
        return customerLoanRepository.save(customerLoan);
    }

    @Override
    public Page<CustomerLoan> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public void sendForwardBackwardLoan(CustomerLoan customerLoan) {
        customerLoanRepository.save(customerLoan);
    }

    @Override
    public List<CustomerLoan> getCustomerLoanByDocumentStatus(DocStatus status) {
        return customerLoanRepository.findFirst5ByDocumentStatusOrderByIdDesc(status);
    }
}
