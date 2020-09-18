package com.sb.solutions.api.customerActivity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.customerActivity.entity.CustomerActivity;
import com.sb.solutions.api.customerActivity.repository.CustomerActivityRepository;

/**
 * @author : Rujan Maharjan on  9/18/2020
 **/
@Service
public class CustomerActivityServiceImpl implements CustomerActivityService {

    private final CustomerActivityRepository activityRepository;

    @Autowired
    public CustomerActivityServiceImpl(
        CustomerActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<CustomerActivity> findAll() {
        return this.activityRepository.findAll();
    }

    @Override
    public CustomerActivity findOne(Long id) {
        Optional<CustomerActivity> customerActivity = activityRepository.findById(id);
        if (customerActivity.isPresent()) {
            return customerActivity.get();
        }
        return null;
    }

    @Override
    public CustomerActivity save(CustomerActivity customerActivity) {
        return activityRepository.save(customerActivity);
    }

    @Override
    public Page<CustomerActivity> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<CustomerActivity> saveAll(List<CustomerActivity> list) {
        return null;
    }
}
