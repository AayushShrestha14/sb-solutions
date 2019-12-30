package com.sb.solutions.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.core.repository.BaseRepository;
import com.sb.solutions.core.repository.BaseSpecBuilder;

public abstract class BaseServiceImpl<T, ID> implements Service<T, ID> {

    private final BaseRepository<T, ID> repository;

    protected BaseServiceImpl(
        BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Page<T> findPageableBySpec(Map<String, String> filterParams, Pageable pageable) {
        final BaseSpecBuilder<T> builder = getSpec(filterParams);
        final Specification<T> spec = builder.build();

        return repository.findAll(spec, pageable);
    }

    @Override
    public List<T> findAllBySpec(Map<String, String> filterParams) {
        final BaseSpecBuilder<T> builder = getSpec(filterParams);
        final Specification<T> spec = builder.build();

        return repository.findAll(spec);
    }

    @Override
    public Optional<T> findOneBySpec(Map<String, String> filterParams) {
        final BaseSpecBuilder<T> builder = getSpec(filterParams);
        final Specification<T> spec = builder.build();

        return repository.findOne(spec);
    }

    @Override
    public Optional<T> findOne(ID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public List<T> saveAll(List<T> list) {
        return repository.saveAll(list);
    }

    protected abstract BaseSpecBuilder<T> getSpec(Map<String, String> filterParams);

}
