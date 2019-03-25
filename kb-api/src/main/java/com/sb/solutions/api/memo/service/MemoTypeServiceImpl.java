package com.sb.solutions.api.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.memo.entity.MemoType;
import com.sb.solutions.api.memo.repository.MemoTypeRepository;
import com.sb.solutions.core.enums.Status;

@Service
public class MemoTypeServiceImpl implements MemoTypeService {

    private final MemoTypeRepository repository;

    public MemoTypeServiceImpl(@Autowired MemoTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MemoType> findByStatus(Status status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<MemoType> saveAll(List<MemoType> types) {
        return repository.saveAll(types);
    }

    @Override
    public void delete(MemoType type) {
        type.setStatus(Status.DELETED);
        repository.save(type);
    }

    @Override
    public List<MemoType> findAll() {
        return repository.findAll();
    }

    @Override
    public MemoType findOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public MemoType save(MemoType memoType) {
        return repository.save(memoType);
    }

    @Override
    public Page<MemoType> findAllPageable(MemoType memoType, Pageable pageable) {
        return repository.findAll(pageable);
    }
}
