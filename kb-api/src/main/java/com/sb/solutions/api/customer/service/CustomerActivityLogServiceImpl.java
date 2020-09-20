package com.sb.solutions.api.customer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sb.solutions.api.customer.entity.CustomerActivityLog;
import com.sb.solutions.api.customer.repository.CustomerActivityLogRepository;
import com.sb.solutions.api.customer.repository.specification.CustomerActivityLogSpecBuilder;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;

/**
 * @author Elvin Shrestha on 9/19/2020
 */
@Service
public class CustomerActivityLogServiceImpl extends
    BaseServiceImpl<CustomerActivityLog, Long> implements CustomerActivityLogService {

    protected CustomerActivityLogServiceImpl(
        CustomerActivityLogRepository repository) {
        super(repository);
    }

    @Override
    protected BaseSpecBuilder<CustomerActivityLog> getSpec(Map<String, String> filterParams) {
        return new CustomerActivityLogSpecBuilder(filterParams);
    }
}
