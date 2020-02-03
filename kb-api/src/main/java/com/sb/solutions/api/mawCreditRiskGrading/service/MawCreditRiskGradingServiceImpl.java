package com.sb.solutions.api.mawCreditRiskGrading.service;

import com.sb.solutions.api.mawCreditRiskGrading.entity.MawCreditRiskGrading;
import com.sb.solutions.api.mawCreditRiskGrading.repository.MawCreditRiskGradingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Amulya Shrestha on 1/31/2020
 **/

@Service
public class MawCreditRiskGradingServiceImpl implements MawCreditRiskGradingService {

    MawCreditRiskGradingRepository mawCreditRiskGradingRepository;

    @Autowired
    public MawCreditRiskGradingServiceImpl(MawCreditRiskGradingRepository mawCreditRiskGradingRepository) {
        this.mawCreditRiskGradingRepository = mawCreditRiskGradingRepository;
    }

    @Override
    public List<MawCreditRiskGrading> findAll() {
        return mawCreditRiskGradingRepository.findAll();
    }

    @Override
    public MawCreditRiskGrading findOne(Long id) {
        return mawCreditRiskGradingRepository.getOne(id);
    }

    @Override
    public MawCreditRiskGrading save(MawCreditRiskGrading mawCreditRiskGrading) {
        return mawCreditRiskGradingRepository.save(mawCreditRiskGrading);
    }

    @Override
    public Page<MawCreditRiskGrading> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<MawCreditRiskGrading> saveAll(List<MawCreditRiskGrading> list) {
        return mawCreditRiskGradingRepository.saveAll(list);
    }
}
