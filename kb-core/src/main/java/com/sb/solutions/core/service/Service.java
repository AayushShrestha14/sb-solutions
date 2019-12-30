package com.sb.solutions.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Service<T, ID> {

    T save(T t);

    List<T> saveAll(List<T> entities);

    Optional<T> findOne(ID id);

    List<T> findAll();

    void delete(T entity);

    void deleteById(ID id);

    Page<T> findPageableBySpec(Map<String, String> filterParams, Pageable pageable);

    List<T> findAllBySpec(Map<String, String> filterParams);

    Optional<T> findOneBySpec(Map<String, String> filterParams);
}
