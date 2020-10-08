package com.sb.solutions.api.fiscalyear.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.fiscalyear.entity.FiscalYear;
import com.sb.solutions.api.fiscalyear.repository.FiscalYearRepository;

@Service
public class FiscalYearServiceImpl implements FiscalYearService {

    private final FiscalYearRepository fiscalYearRepository;
    protected FiscalYearServiceImpl(
        @Autowired FiscalYearRepository repository) {
        this.fiscalYearRepository = repository;
    }

    @Override
    public List<FiscalYear> findAll() {
        return fiscalYearRepository.findAll();
    }

    @Override
    public List<FiscalYear> findAll(Object search) {
        return null;
    }

    @Override
    public FiscalYear findOne(Long id) {
        return fiscalYearRepository.getOne(id);
    }

    @Override
    public FiscalYear save(FiscalYear fiscalYear) {
        return fiscalYearRepository.save(fiscalYear);
    }

    @Override
    public Page<FiscalYear> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<FiscalYear> saveAll(List<FiscalYear> list) {
        return fiscalYearRepository.saveAll(list);
    }
}
