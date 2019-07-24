package com.sb.solutions.api.customerOtp.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.customerOtp.entity.CustomerOtp;
import com.sb.solutions.api.customerOtp.repository.CustomerOtpRepository;
import com.sb.solutions.core.utils.date.DateManipulator;
import com.sb.solutions.core.utils.string.StringUtil;

@Service
public class CustomerOtpServiceImpl implements CustomerOtpService {

    private final CustomerOtpRepository repository;

    public CustomerOtpServiceImpl(
        CustomerOtpRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public List<CustomerOtp> findAll() {
        return repository.findAll();
    }

    @Override
    public CustomerOtp findOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public CustomerOtp save(CustomerOtp customerOtp) {
        DateManipulator dateManipulator = new DateManipulator(new Date());
        customerOtp.setOtp(StringUtil.generate(4));
        customerOtp.setExpiry(dateManipulator.addMinutes(5));
        return repository.save(customerOtp);
    }

    @Override
    public Page<CustomerOtp> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
