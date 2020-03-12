package com.sb.solutions.api.insurance.service;

import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.repository.InsuranceRepository;
import com.sb.solutions.api.insurance.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    InsuranceRepository insuranceRepository;

    @Override
    public List<Insurance> findAll() {
        return insuranceRepository.findAll();
    }

    @Override
    public Insurance findOne(Long id) {
        return insuranceRepository.getOne(id);
    }

    @Override
    public Insurance save(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    @Override
    public Page<Insurance> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<Insurance> saveAll(List<Insurance> list) {
        return insuranceRepository.saveAll(list);
    }

}
