package com.sb.solutions.api.crg.service;

import com.sb.solutions.api.crg.entity.CrgGroup;
import com.sb.solutions.api.crg.repository.CrgGroupRepository;
import com.sb.solutions.core.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
@Service
@RequiredArgsConstructor
public class CrgGroupServiceImpl implements CrgGroupService {
    private final CrgGroupRepository crgGroupRepository;

    @Override
    public List<CrgGroup> findAll() {
        return crgGroupRepository.findAll();
    }

    @Override
    public CrgGroup findOne(Long id) {
        return crgGroupRepository.getOne(id);
    }

    @Override
    public CrgGroup save(CrgGroup crgGroup) {
        return crgGroupRepository.save(crgGroup);
    }

    @Override
    public Page<CrgGroup> findAllPageable(Object t, Pageable pageable) {
        return crgGroupRepository.findAll(pageable);
    }

    @Override
    public List<CrgGroup> saveAll(List<CrgGroup> list) {
        return crgGroupRepository.saveAll(list);
    }

    @Override
    public CrgGroup findByStatus(Status status) {
        return crgGroupRepository.findByStatus(status);
    }
}
